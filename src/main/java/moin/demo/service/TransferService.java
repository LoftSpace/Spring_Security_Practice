package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.domain.User;
import moin.demo.dto.QuoteCreateRequestDto;
import moin.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final CurrencyServiceFactory currencyServiceFactory;
    private final QuoteService quoteService;
    private final UserRepository userRepository;

    public Quote createQuote(QuoteCreateRequestDto quoteCreateRequestDto, String userId){

        if(quoteCreateRequestDto.getAmount() <= 0){
            throw new IllegalArgumentException("NEGATIVE_NUMBER");
        }
        // 각 통화에 따른 환율 적용
        CurrencyService currencyService = currencyServiceFactory.getService(quoteCreateRequestDto.getTargetCurrency());
        Quote quote = currencyService.calculateQuote(quoteCreateRequestDto.getAmount());
        quote.setUserId(userId);
        return quoteService.saveQuote(quote);

    }
    public void transfer(String userId, String quoteId) throws  IllegalArgumentException{
        Quote quote = quoteService.getQuote(quoteId);
        User user = userRepository.findByUserId(userId).get();

        checkIsQuoteExpired(quote);
        checkTodayTransderedAmount(user, quote);
        userRepository.updateTransferCountAndAmount(userId,quote.getSourceAmount());
    }

    private static void checkTodayTransderedAmount(User user, Quote quote) {
        String userType = user.getIdType();
        if(userType.equals("REG_NO")) {
            if(quote.getSourceAmount() + user.getTodayTransferUsdAmount() >= 1000) {
                throw new IllegalArgumentException("LIMIT_EXCESS");
            }
        }
        else if(userType.equals("BUSSNESS_TYPE")) {
            if(quote.getSourceAmount() + user.getTodayTransferUsdAmount() >= 5000) {
                throw new IllegalArgumentException("LIMIT_EXCESS");
            }
        }
    }

    private static void checkIsQuoteExpired(Quote quote) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);

        if(formattedCurrentDateTime.compareTo(quote.getExpireTime()) >= 0){
            throw new IllegalArgumentException("QUOTE_EXPIRED");
        }
    }

}

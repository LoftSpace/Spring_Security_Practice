package moin.demo.service.CurrencyService;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.ExchangeRateFromUpbitDto;
import moin.demo.repository.QuoteRepository;
import moin.demo.service.TransferService.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Service("JPYService")
@RequiredArgsConstructor
public class JPYService implements CurrencyService {
    public final ExchangeRateService exchangeRateService;
    private final QuoteRepository quoteRepository;

    @Override
    public Quote calculateQuote(Long amount) {
        Currency jpy = Currency.getInstance("JPY");
        int usdFractionDigits = jpy.getDefaultFractionDigits();
        double multiplier = Math.pow(10, usdFractionDigits);
        double totalFee = getTotalFee(amount);

        if(amount - totalFee > 0) {
            double exchangeRate = getExchangeRate(multiplier);
            double targetAmount = (amount - totalFee) * multiplier / exchangeRate * multiplier;
            targetAmount = Math.round(targetAmount * multiplier) / multiplier;
            String expireTime = getExpireTime();

            return Quote.builder()
                    .sourceAmount(amount)
                    .exchangeRate(exchangeRate)
                    .expireTime(expireTime)
                    .targetAmount(targetAmount)
                    .build();

        } else {
            throw new IllegalArgumentException("NEGATIVE_NUMBER");
        }



    }

    private static String getExpireTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime
                .plusMinutes(10)
                .format(formatter);
        return formattedDateTime;

    }
    private static double getTotalFee(Long amount) {
        double fixedFee = 3000;
        double feeRate = 0.1 / 100;
        double totalFee;
        totalFee = (amount * (long)(feeRate * 1000))/1000 + fixedFee;
        return totalFee;
    }
    private double getExchangeRate(double multiplier){
        ExchangeRateFromUpbitDto exchangeRateFromUpbitDto = exchangeRateService.getExchangeRate("USD");
        double exchangeRate = Math.round(exchangeRateFromUpbitDto.getBasePrice() * multiplier) / multiplier;
        double rate = (exchangeRate * multiplier) / (exchangeRateFromUpbitDto.getCurrencyUnit() * multiplier);
        return Math.round(rate * multiplier) / multiplier;
    }
}

package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.QuoteRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final CurrencyServiceFactory currencyServiceFactory;

    public void getQuote(QuoteRequestDto quoteRequestDto){
        if(quoteRequestDto.getAmount() <= 0){
            throw new IllegalArgumentException("NEGATIVE_NUMBER");
        }
        CurrencyService currencyService = currencyServiceFactory.getService(quoteRequestDto.getTargetCurrency());
        // 각 통화에 따른 환율 적용
        Quote quote = currencyService.calculateQuote(quoteRequestDto.getAmount());


    }

}

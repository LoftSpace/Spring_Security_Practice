package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.ExchangeRateFromUpbitDto;
import org.springframework.stereotype.Service;

@Service("USDService")
@RequiredArgsConstructor
public class USDService implements CurrencyService{

    public final ExchangeRateService exchangeRateService;

    @Override
    public Quote calculateQuote(Long amount) {
        ExchangeRateFromUpbitDto exchangeRateFromUpbitDto = exchangeRateService.getExchangeRate("USD");

        return null;
    }
}

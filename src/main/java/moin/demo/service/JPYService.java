package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.ExchangeRateFromUpbitDto;
import org.springframework.stereotype.Service;

@Service("JPYService")
@RequiredArgsConstructor
public class JPYService implements CurrencyService{
    public final ExchangeRateService exchangeRateService;

    @Override
    public Quote calculateQuote(Long amount) {
        ExchangeRateFromUpbitDto exchangeRateFromUpbit = exchangeRateService.getExchangeRate("JPY");
        return null;
    }
}

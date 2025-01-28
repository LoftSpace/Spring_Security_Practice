package moin.demo.service.CurrencyService;


import moin.demo.domain.Quote;

public interface CurrencyService {
    Quote calculateQuote(Long amount);
}


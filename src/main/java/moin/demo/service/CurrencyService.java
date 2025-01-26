package moin.demo.service;


import moin.demo.domain.Quote;

public interface CurrencyService {
    Quote calculateQuote(Long amount);
}


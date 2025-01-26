package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.ExchangeRateFromUpbitDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Currency;

@Service("USDService")
@RequiredArgsConstructor
public class USDService implements CurrencyService{

    public final ExchangeRateService exchangeRateService;

    @Override
    public Quote calculateQuote(Long amount) {
        Currency usd = Currency.getInstance("USD");
        int usdFractionDigits = usd.getDefaultFractionDigits();
        double multiplier = Math.pow(10, usdFractionDigits);
        double totalFee = getTotalFee(amount);

        if(amount - totalFee > 0) {
            double exchangeRate = getExchangeRate(multiplier);
            double targetAmount = (amount - totalFee) * multiplier / exchangeRate * multiplier;
            LocalDateTime expireTime = getExpireTime();
            //quote 생성 및 디비 저장
            Quote quote = Quote.builder()
                    .quoteId()
                    .exchangeRate(exchangeRate)
                    .expireTime(expireTime)
                    .targetAmount(targetAmount)
                    .build();

            return quote;
        } else {
            throw new IllegalArgumentException("NEGATIVE_NUMBER");
        }

    }

    private static LocalDateTime getExpireTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.plusMinutes(10);

    }

    private static double getTotalFee(Long amount) {
        double fixedFee;
        double feeRate;
        double totalFee;

        if(amount <= 1000000){
            fixedFee = 1000;
            feeRate = 0.2 / 100;
        }
        else {
            fixedFee = 3000;
            feeRate = 0.1 / 100;
        }

        totalFee = (amount * (long)(feeRate * 1000))/1000 + fixedFee;
        return totalFee;
    }

    private double getExchangeRate(double multiplier){
        ExchangeRateFromUpbitDto exchangeRateFromUpbitDto = exchangeRateService.getExchangeRate("USD");
        double rate = (long)(exchangeRateFromUpbitDto.getBasePrice() * multiplier) / exchangeRateFromUpbitDto.getCurrencyUnit() * multiplier;
        return rate;
    }
}

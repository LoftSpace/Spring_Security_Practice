package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.ExchangeRateFromUpbitDto;
import moin.demo.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

@Service("USDService")
@RequiredArgsConstructor
public class USDService implements CurrencyService{

    private final ExchangeRateService exchangeRateService;
    private final QuoteRepository quoteRepository;

    @Override
    public Quote calculateQuote(Long amount) {
        Currency usd = Currency.getInstance("USD");
        int usdFractionDigits = usd.getDefaultFractionDigits();
        double multiplier = Math.pow(10, usdFractionDigits);
        double totalFee = getTotalFee(amount);

        if(amount - totalFee > 0) {
            double exchangeRate = getExchangeRate(multiplier);
            double targetAmount = (amount - totalFee) * multiplier / exchangeRate * multiplier;
            targetAmount = Math.round(targetAmount * multiplier) / multiplier;
            String expireTime = getExpireTime();
            //quote 생성 및 디비 저장
            return  Quote.builder()
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
        double exchangeRate = Math.round(exchangeRateFromUpbitDto.getBasePrice() * multiplier) / multiplier;
        double rate = (exchangeRate * multiplier) / (exchangeRateFromUpbitDto.getCurrencyUnit() * multiplier);
        return Math.round(rate * multiplier) / multiplier;
    }
}

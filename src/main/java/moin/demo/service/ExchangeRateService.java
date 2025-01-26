package moin.demo.service;

import lombok.RequiredArgsConstructor;
import moin.demo.dto.ExchangeRateFromUpbitDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

   // private final WebClient webClient;

    public ExchangeRateFromUpbitDto getExchangeRate(String currency) {
        String codeParameter = getCurrencyCode(currency);
        URI uri = UriComponentsBuilder
                .fromUriString("https://crix-api-cdn.upbit.com")
                .path("/v1/forex/recent")
                .queryParam("codes",codeParameter)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
        System.out.println(uri.toString());
        RestTemplate restTemplate = new RestTemplate();
        //ResponseEntity<ExchangeRateFromUpbitDto> exchangeRateFromUpbit = restTemplate.getForEntity(uri, ExchangeRateFromUpbitDto.class);
        ResponseEntity<ExchangeRateFromUpbitDto[]> exchangeRateFromUpbit = restTemplate.getForEntity(uri, ExchangeRateFromUpbitDto[].class);

        return exchangeRateFromUpbit.getBody()[0];

    }
    private String getCurrencyCode(String currency) {
        switch (currency.toUpperCase()) {
            case "USD":
                return "FRX.KRWUSD";
            case "JPY":
                return "FRX.KRWJPY";
            default:
                throw new IllegalArgumentException("Unsupported currency: " + currency);
        }
    }
}

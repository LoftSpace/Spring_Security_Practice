package moin.demo.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeRateFromUpbitDto {
    private String code;
    private String currencyCode;
    private Double basePrice;
    private Double currencyUnit;

}

package moin.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class QuoteCreateRequestDto {
    private long amount;
    private String targetCurrency;


}

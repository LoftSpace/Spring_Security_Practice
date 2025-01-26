package moin.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor()
public class QuoteRequestDto {
    private long amount;
    private String targetCurrency;

}

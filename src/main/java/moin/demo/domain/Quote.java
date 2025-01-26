package moin.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "Quote")
public class Quote {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "quoteId", nullable = false)
    private Long quoteId;

    @Column(name = "exchangeRate", nullable = false)
    private Double exchangeRate;

    @Column(name = "expireTime", nullable = false)
    private LocalDateTime expireTime;

    @Column(name = "targetAmount",nullable = false)
    private Double targetAmount;

    protected Quote() {}
    @Builder
    public Quote(Long quoteId, Double exchangeRate, LocalDateTime expireTime, Double targetAmount){
        this.quoteId = quoteId;
        this.exchangeRate = exchangeRate;
        this.expireTime = expireTime;
        this.targetAmount = targetAmount;
    }


}

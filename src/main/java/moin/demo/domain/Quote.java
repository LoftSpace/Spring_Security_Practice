package moin.demo.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
@Table(name = "Quote")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "quoteId", nullable = false)
    private Long quoteId;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "sourceAmount", nullable = false)
    private Long sourceAmount;

    @Column(name = "exchangeRate", nullable = false)
    private Double exchangeRate;

    @Column(name = "expireTime", nullable = false)
    private String expireTime;

    @Column(name = "targetAmount",nullable = false)
    private Double targetAmount;

    protected Quote() {}

    public void setUserId(String userId){
        this.userId = userId;
    }

    @Builder
    public Quote(Long quoteId, String userId,Long sourceAmount,Double exchangeRate, String expireTime, Double targetAmount){
        this.quoteId = quoteId;
        this.userId = userId;
        this.sourceAmount = sourceAmount;
        this.exchangeRate = exchangeRate;
        this.expireTime = expireTime;
        this.targetAmount = targetAmount;

    }


}

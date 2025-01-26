package moin.demo.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "Quote")
public class Quote {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "quoteId", nullable = false)
    private Long quoteId;

    @Column(name = "exchangeRate", nullable = false)
    private Long exchangeRate;

    @Column(name = "expireTime", nullable = false)
    private LocalDateTime expireTime;

    @Column(name = "targetAmount",nullable = false)
    private Long targetAmount;



}

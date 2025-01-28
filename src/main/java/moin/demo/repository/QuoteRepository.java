package moin.demo.repository;

import moin.demo.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote,Long> {
    Optional<Quote> findByQuoteId(Long quoteId);

}

package moin.demo.service.TransferService;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.repository.QuoteRepository;
import moin.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Quote saveQuote(Quote quote){
        userRepository.findByUserId(quote.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        return quoteRepository.save(quote);
    }

    public Quote getQuote(String quoteId){
        return quoteRepository.findByQuoteId(Long.parseLong(quoteId)).get();
    }

}

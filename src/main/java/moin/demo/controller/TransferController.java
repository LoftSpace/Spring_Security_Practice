package moin.demo.controller;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.QuoteCreateRequestDto;
import moin.demo.dto.QuoteRequestDto;
import moin.demo.service.QuoteService;
import moin.demo.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    private final QuoteService quoteService;

    @GetMapping("/request")
    public ResponseEntity<?> requestQuote(@RequestBody QuoteRequestDto quoteRequestDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        String quoteId = quoteRequestDto.getQuoteId();
        transferService.handleTransfer(userId,quoteId);

    }

    @PostMapping("/quote")
    public ResponseEntity<?> createQuote(@RequestBody QuoteCreateRequestDto quoteCreateRequestDto){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getName();
            Quote quote = transferService.createQuote(quoteCreateRequestDto,userId);

            return ResponseEntity.ok().body(
                    Map.of("resultCode",200,"resulrMsg","OK","quote",quote)
            );

        } catch(IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            if(errorMessage.equals("NEGATIVE_NUMBER")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("resultCode", 400, "resultMsg", "NEGATIVE_NUMBER"));
            }
            else if(errorMessage.equals("유저 없음")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("resultCode", 400, "resultMsg", "유저 없음"));
            }
            else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        Map.of("resultCode", 500, "resultMsg", "UNKNOWN_ERROR"));
            }
        }
    }

}

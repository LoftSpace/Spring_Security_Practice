package moin.demo.controller;

import lombok.RequiredArgsConstructor;
import moin.demo.domain.Quote;
import moin.demo.dto.QuoteRequestDto;
import moin.demo.service.TransferService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    @PostMapping("/quote")
    public ResponseEntity<?> getQuote(@RequestBody QuoteRequestDto quoteRequestDto){
        try {
            Quote quote = transferService.getQuote(quoteRequestDto);
            return ResponseEntity.ok().body(
                    Map.of("resultCode",200,"resulrMsg","OK","quote",quote)
            );

        } catch(IllegalArgumentException e) {
            return ResponseEntity.ok().body(
                    Map.of("resultCode",400,"resultMsg",e));
        }
    }

}

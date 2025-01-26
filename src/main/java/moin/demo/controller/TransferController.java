package moin.demo.controller;

import lombok.RequiredArgsConstructor;
import moin.demo.dto.QuoteRequestDto;
import moin.demo.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;
    @PostMapping("/quote")
    public ResponseEntity<?> getQuote(@RequestBody QuoteRequestDto quoteRequestDto){
        transferService.getQuote(quoteRequestDto);
    }

}

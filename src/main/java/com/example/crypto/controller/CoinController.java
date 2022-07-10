package com.example.crypto.controller;


import com.example.crypto.dto.CoinDto;
import com.example.crypto.model.Symbol;
import com.example.crypto.service.LoreCoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CoinController {

    private final LoreCoinService loreCoinService;

    @GetMapping("/coins")
    public ResponseEntity<?> getAvailableCoins() {
        return new ResponseEntity<>(loreCoinService.getAvailableCoins(), HttpStatus.OK);
    }

    @GetMapping("/coin/{symbol}")
    public ResponseEntity<CoinDto> getCoinPrice(@PathVariable String symbol) {
        Symbol smb = Symbol.getSymbolByString(symbol.toUpperCase());
        return new ResponseEntity<>(loreCoinService.getCoinPriceBySymbol(smb), HttpStatus.OK);
    }

}

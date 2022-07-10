package com.example.crypto.dto;

import com.example.crypto.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Component
public class CoinDto {

    private Symbol symbol;
    private double price;
}

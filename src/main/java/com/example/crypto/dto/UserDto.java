package com.example.crypto.dto;

import com.example.crypto.model.Symbol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class UserDto {

    private String username;

    @Enumerated(EnumType.STRING)
    private Symbol symbol;
    private double price;
}

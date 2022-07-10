package com.example.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;
    private Long coinCode;

    @Enumerated(EnumType.STRING)
    private Symbol symbol;

    private String name;
    private double price;

    public Coin(Long coinCode, Symbol symbol, String name, double price) {
        this.coinCode = coinCode;
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}



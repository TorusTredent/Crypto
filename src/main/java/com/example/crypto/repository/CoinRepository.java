package com.example.crypto.repository;

import com.example.crypto.model.Coin;
import com.example.crypto.model.Symbol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findCoinBySymbol(Symbol symbol);
    List<Coin> findAll();
}

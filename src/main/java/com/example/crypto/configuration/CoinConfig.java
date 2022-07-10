package com.example.crypto.configuration;

import com.example.crypto.model.Coin;
import com.example.crypto.model.Symbol;
import com.example.crypto.repository.CoinRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CoinConfig {

    @Bean
    CommandLineRunner commandLineRunner(CoinRepository coinRepository) {
        return args -> {
            Coin bitcoin = new Coin(
                    90L,
                    Symbol.BTC,
                    "Bitcoin",
                    20000
            );
            Coin ethereum = new Coin(
                    80L,
                    Symbol.ETH,
                    "Ethereum",
                    1218.69
            );
            Coin solana = new Coin(
                    48543L,
                    Symbol.SOL,
                    "Solana",
                    38.32
            );
            coinRepository.saveAll(
                    List.of(bitcoin, ethereum, solana)
            );
        };
    }
}

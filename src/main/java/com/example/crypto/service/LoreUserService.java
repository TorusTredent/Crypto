package com.example.crypto.service;

import com.example.crypto.exception.CoinNotFoundException;
import com.example.crypto.exception.UserIsAlreadyExistException;
import com.example.crypto.model.Coin;
import com.example.crypto.model.Symbol;
import com.example.crypto.model.User;
import com.example.crypto.repository.CoinRepository;
import com.example.crypto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;

    public User notify(String username, Symbol symbol) {
        if (userRepository.existsByUsername(username)) {
            throw new UserIsAlreadyExistException(String.format("User {} already exist %s.", username));
        }
        Coin coin = coinRepository.findCoinBySymbol(symbol)
                .orElseThrow(() -> new CoinNotFoundException(String.format("Coin %S not found.", symbol)));
        return userRepository.save(new User(
                username,
                coin.getSymbol(),
                coin.getPrice()
        ));
    }
}

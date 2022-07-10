package com.example.crypto.service;

import com.example.crypto.dto.UserRegDto;
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
public class LoreUserService implements UserService{

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;

    @Override
    public User notify(UserRegDto user) {
        if (userRepository.existsByUsernameAndSymbol(user.getUsername(), user.getSymbol())) {
            throw new UserIsAlreadyExistException(String.format("User {} already exist %s.", user.getUsername()));
        }
        Coin coin = coinRepository.findCoinBySymbol(user.getSymbol())
                .orElseThrow(() -> new CoinNotFoundException(String.format("Coin %S not found.", user.getSymbol())));
        return userRepository.save(new User(
                user.getUsername(),
                coin.getSymbol(),
                coin.getPrice()
        ));
    }
}

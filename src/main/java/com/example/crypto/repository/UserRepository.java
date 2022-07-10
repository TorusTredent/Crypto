package com.example.crypto.repository;

import com.example.crypto.model.Coin;
import com.example.crypto.model.Symbol;
import com.example.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameAndSymbol(String username, Symbol symbol);
    Optional<List<User>> findAllBySymbol(Symbol symbol);
}

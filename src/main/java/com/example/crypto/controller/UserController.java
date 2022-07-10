package com.example.crypto.controller;

import com.example.crypto.dto.UserDto;
import com.example.crypto.dto.UserRegDto;
import com.example.crypto.model.User;
import com.example.crypto.service.LoreCoinService;
import com.example.crypto.service.LoreUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final LoreUserService loreUserService;

    @GetMapping("/user")
    public ResponseEntity<?> notify(@RequestBody UserRegDto user) {
        User saveUser = loreUserService.notify(user);
        return new ResponseEntity<>(new UserDto(saveUser.getUsername(), saveUser.getSymbol(), saveUser.getPrice()),
                HttpStatus.OK);
    }
}

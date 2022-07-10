package com.example.crypto.service;

import com.example.crypto.dto.UserRegDto;
import com.example.crypto.model.Symbol;
import com.example.crypto.model.User;

public interface UserService {
    User notify(UserRegDto user);
}

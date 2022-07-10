package com.example.crypto.service;

import com.example.crypto.dto.CoinDto;
import com.example.crypto.dto.CoinResponseDto;
import com.example.crypto.model.Coin;
import com.example.crypto.model.Symbol;

import java.util.List;

public interface CoinService {

    List<Coin> saveCoinsFromApi();

    List<Coin> getCoinsFromCryptoLoreApi ();
    List<CoinResponseDto> getAvailableCoins();
    CoinDto getCoinPriceBySymbol(Symbol symbol);
}

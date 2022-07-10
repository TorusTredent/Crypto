package com.example.crypto.service;

import com.example.crypto.dto.CoinApiDto;
import com.example.crypto.dto.CoinDto;
import com.example.crypto.dto.CoinResponseDto;
import com.example.crypto.exception.CoinNotFoundException;
import com.example.crypto.model.Coin;
import com.example.crypto.model.Symbol;
import com.example.crypto.repository.CoinRepository;
import com.example.crypto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoreCoinService implements CoinService {

    private final CoinRepository coinRepository;
    private final UserRepository userRepository;

    private HttpClient httpClient = HttpClientBuilder.create().build();
    private ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
    private RestTemplate restTemplate = new RestTemplate(requestFactory);

    private static final String URL = "https://api.coinlore.net/api/ticker/?id=90,80,48543";

    @Override
    public List<Coin> getCoinsFromCryptoLoreApi() {
        try {
            CoinApiDto[] response;
            response = restTemplate.getForObject(new URI(URL), CoinApiDto[].class);
            return Arrays.stream(response)
                    .map(this::toCoin)
                    .collect(Collectors.toList());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public List<Coin> saveCoinsFromApi() {
        List<Coin> coinsFromApi = getCoinsFromCryptoLoreApi();
        List<Coin> coinsDb = coinRepository.findAll();
        checkPriceChange(coinsDb, coinsFromApi);
        changeCoinApiIds(coinsDb, coinsFromApi);
        return coinRepository.saveAll(coinsFromApi);
    }

    @Override
    public List<CoinResponseDto> getAvailableCoins() {
        List<Coin> coins = coinRepository.findAll();
        if (coins.isEmpty()) {
            throw new CoinNotFoundException(String.format("Coins not found."));
        }
        return coins.stream()
                .map(this::refactCoinsToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CoinDto getCoinPriceBySymbol(Symbol symbol) {
        Coin coin = coinRepository.findCoinBySymbol(symbol).
                orElseThrow(() -> new CoinNotFoundException(String.format("Coin %S not found.", symbol)));
        return new CoinDto(
                coin.getSymbol(),
                coin.getPrice()
        );
    }

    private CoinResponseDto refactCoinsToResponse(Coin coin) {
        return new CoinResponseDto(
                coin.getCoinCode(),
                coin.getSymbol(),
                coin.getName()
        );
    }

    private Coin toCoin(CoinApiDto coinApiDto) {
        return new Coin(
                Long.parseLong(coinApiDto.getId()),
                Symbol.valueOf(coinApiDto.getSymbol()),
                coinApiDto.getName(),
                Double.parseDouble(coinApiDto.getPrice_usd())
        );
    }

    private void checkPriceChange(List<Coin> coinsDb, List<Coin> coinsFromApi) {
        coinsDb.forEach(coin -> coinsFromApi.stream()
                .filter(coinFromApi -> coin.getSymbol() == coinFromApi.getSymbol())
                .mapToDouble(coinFromApi -> coinFromApi.getPrice() * 100 / coin.getPrice() - 100)
                .filter(percent -> percent >= 1 || percent <= -1)
                .forEach(percent -> userRepository.findAllBySymbol(coin.getSymbol())
                        .ifPresent(users -> users.forEach(user -> {
                            log.warn(coin.getSymbol() + " - " + user.getUsername() + " - " + percent + "%");
                        }))));
    }

    private void changeCoinApiIds(List<Coin> coinsDb, List<Coin> coinsApi) {
        for (Coin coinApi : coinsApi) {
            for (Coin coinDb : coinsDb) {
                if (coinApi.getSymbol() == coinDb.getSymbol()) {
                    coinApi.setId(coinDb.getId());
                }
            }
        }
    }
}

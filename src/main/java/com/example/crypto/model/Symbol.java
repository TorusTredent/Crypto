package com.example.crypto.model;

import com.example.crypto.exception.SymbolNotFoundException;

import javax.transaction.SystemException;

public enum Symbol {
    BTC("BTC"),
    ETH("ETH"),
    SOL("SOL");

    private String symbol;

    public static Symbol getSymbolByString(String str) {
        for (Symbol s : Symbol.values()) {
            if (s.symbol.equals(str)) {
                return s;
            }
        }
        throw new SymbolNotFoundException(String.format("Symbol %S not found.", str));
    }

    Symbol(String symbol) {
        this.symbol = symbol;
    }
}

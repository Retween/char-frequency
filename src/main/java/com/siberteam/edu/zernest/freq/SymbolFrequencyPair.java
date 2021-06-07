package com.siberteam.edu.zernest.freq;

public class SymbolFrequencyPair implements Comparable<SymbolFrequencyPair> {
    private final Character symbol;
    private final Integer frequency;

    public SymbolFrequencyPair(Character symbol, Integer frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public Character getSymbol() {
        return symbol;
    }

    public Integer getFrequency() {
        return frequency;
    }

    @Override
    public int compareTo(SymbolFrequencyPair o) {
        return Integer.compare(this.getFrequency(), o.getFrequency());
    }

    @Override
    public String toString() {
        return "SymbolFrequencyPair{" +
                "symbol=" + symbol +
                ", frequency=" + frequency +
                '}';
    }
}

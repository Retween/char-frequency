package com.siberteam.edu.zernest.freq;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CharIntMapToStringFormatter {
    private final Map<Character, AtomicInteger> map;
    private final AtomicInteger sumOfElements;

    public CharIntMapToStringFormatter(Map<Character, AtomicInteger> map,
                                       AtomicInteger sumOfElements) {
        this.map = map;
        this.sumOfElements = sumOfElements;
    }

    private List<SymbolFrequencyPair> getPairListFromMap(
            Map<Character, AtomicInteger> map) {
        List<SymbolFrequencyPair> pairsList = new ArrayList<>();

        for (Map.Entry<Character, AtomicInteger> entry : map.entrySet()) {
            pairsList.add(new SymbolFrequencyPair(
                    entry.getKey(),
                    entry.getValue().get()));
        }

        return pairsList;
    }

    private List<SymbolFrequencyPair> getSortedByFreqTopList(
            Map<Character, AtomicInteger> map) {
        List<SymbolFrequencyPair> pairsList = getPairListFromMap(map);

        pairsList.sort(Comparator.reverseOrder());

        return pairsList.subList(0, 5);
    }

    public String getHistogram(boolean percentage, boolean frequency,
                               Character histSymbol) throws IOException {
        StringBuilder histogram = new StringBuilder();
        List<SymbolFrequencyPair> freqPairsList = getSortedByFreqTopList(map);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        histogram.append("time: ")
                .append(sdf.format(Calendar.getInstance().getTime()))
                .append("\n");


        for (SymbolFrequencyPair symbolFrequencyPair : freqPairsList) {
            double percent = 0;

            if (percentage || histSymbol != null) {
                percent = symbolFrequencyPair.getFrequency() * 100.0
                        / sumOfElements.get();
            }

            histogram.append(symbolFrequencyPair.getSymbol());
            if (percentage) {
                histogram.append(String.format("(%.1f%%)", percent));
            }

            if (frequency) {
                histogram.append("(")
                        .append(symbolFrequencyPair.getFrequency())
                        .append(")");
            }

            if (histSymbol != null) {
                histogram.append(":");
                for (int j = 1; j < percent; j++) {
                    histogram.append(histSymbol);
                }
            }

            histogram.append('\n');
        }
        histogram.append('\n');

        return histogram.toString();
    }

    @Override
    public String toString() {
        return "CharIntMapToStringFormatter{" +
                "sumOfElements=" + sumOfElements +
                '}';
    }
}

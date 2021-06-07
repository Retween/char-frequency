package com.siberteam.edu.zernest.freq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InputStreamToMapCharFrequencyCounter implements Runnable {
    private final InputStream inputStream;
    private final Map<Character, AtomicInteger> freqMap;
    private final AtomicInteger sumOfElements = new AtomicInteger(0);
    private boolean inputStreamReadingDone = false;

    public InputStreamToMapCharFrequencyCounter(InputStream inputStream) {
        this.inputStream = inputStream;
        freqMap = new HashMap<>();
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            int r;
            while ((r = br.read()) != -1) {
                char c = (char) r;
                if (c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') {
                    c = Character.toLowerCase(c);
                    freqMap.computeIfAbsent(c, k -> new AtomicInteger(0))
                            .getAndIncrement();
                    sumOfElements.getAndIncrement();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            inputStreamReadingDone = true;
        }
    }

    public Map<Character, AtomicInteger> getFreqMap() {
        return freqMap;
    }

    public AtomicInteger getSumOfElements() {
        return sumOfElements;
    }

    public boolean isInputStreamEmpty() {
        return inputStreamReadingDone;
    }

    @Override
    public String toString() {
        return "InputStreamToMapCharFrequencyCounter{" +
                "inputStream=" + inputStream +
                ", sumOfElements=" + sumOfElements.get() +
                ", inputStreamReadingDone=" + inputStreamReadingDone +
                '}';
    }
}


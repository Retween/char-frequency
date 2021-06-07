package com.siberteam.edu.zernest.freq;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            if (args.length != 2) {
                throw new CharFrequencyAppException(
                        CharFrequencyExitCode.COMMAND_LINE_USAGE);
            }

            File inputFile = new File(args[0]);
            File outputFile = new File(args[1]);

            if (!inputFile.exists()) {
                throw new CharFrequencyAppException(
                        CharFrequencyExitCode.CANNOT_OPEN_INPUT,
                        inputFile.getName());
            }

            if (outputFile.exists() && outputFile.isFile()) {
                throw new CharFrequencyAppException(
                        CharFrequencyExitCode.FILE_ALREADY_EXISTS,
                        outputFile.getName());
            }

            inputStream = new FileInputStream(inputFile);
            outputStream = new FileOutputStream(outputFile);

            InputStreamToMapCharFrequencyCounter charFrequencyCounter =
                    new InputStreamToMapCharFrequencyCounter(inputStream);
            Thread inputReadingThread = new Thread(charFrequencyCounter);
            inputReadingThread.start();

            Thread.sleep(1000);

            CharIntMapToStringFormatter mapToStringFormatter =
                    new CharIntMapToStringFormatter(
                            charFrequencyCounter.getFreqMap(),
                            charFrequencyCounter.getSumOfElements());

            do {
                String s = mapToStringFormatter.getHistogram(true, true, '#');
                outputStream.write(s.getBytes(StandardCharsets.UTF_8));

                TimeUnit.SECONDS.sleep(1);
            } while (!charFrequencyCounter.isInputStreamEmpty());

        } catch (IOException | RuntimeException e) {
            handleException(CharFrequencyExitCode.INPUT_OUTPUT, e);
        } catch (InterruptedException e) {
            handleException(CharFrequencyExitCode.INTERRUPTED, e);
        } catch (CharFrequencyAppException e) {
            handleException(e.getExitCode(), e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void handleException(CharFrequencyExitCode exitCode,
                                       Exception e) {
        log(exitCode.getDescription() + "\n" +
                e.getMessage() + "\n" +
                e.getCause());
        System.exit(exitCode.getCode());
    }

    public static void log(String message) {
        System.out.println(message);
    }
}

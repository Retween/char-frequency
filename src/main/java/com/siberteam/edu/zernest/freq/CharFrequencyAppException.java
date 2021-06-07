package com.siberteam.edu.zernest.freq;

public class CharFrequencyAppException extends Exception {
    private final CharFrequencyExitCode exitCode;

    public CharFrequencyAppException(CharFrequencyExitCode exitCode,
                                     String message) {
        super(message);
        this.exitCode = exitCode;
    }

    public CharFrequencyAppException(CharFrequencyExitCode exitCode) {
        super("Char Frequency Exception");
        this.exitCode = exitCode;
    }

    public CharFrequencyExitCode getExitCode() {
        return exitCode;
    }

    @Override
    public String toString() {
        return "CharFrequencyAppException{" +
                "exitCode=" + exitCode +
                ", message=" + getMessage() +
                '}';
    }
}

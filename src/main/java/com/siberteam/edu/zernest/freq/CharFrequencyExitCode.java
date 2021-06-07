package com.siberteam.edu.zernest.freq;

public enum CharFrequencyExitCode {
    COMMAND_LINE_USAGE(64, "First argument: input file,\n" +
            "Second argument:output file."),
    CANNOT_OPEN_INPUT(66, "File not found"),
    INPUT_OUTPUT(74, "Input/Output exception was caught"),
    FILE_ALREADY_EXISTS(74, "File already exists"),
    INTERRUPTED(1, "Interrupted exception was caught");

    private final int code;
    private final String description;

    CharFrequencyExitCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CharFrequencyExitCode{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}

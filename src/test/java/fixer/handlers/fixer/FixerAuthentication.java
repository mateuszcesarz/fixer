package fixer.handlers.fixer;

import lombok.SneakyThrows;

import java.util.Properties;

public class FixerAuthentication {
    private final String apikey;

    @SneakyThrows
    public FixerAuthentication() {
        this.apikey = System.getProperty("fixer.apikey");

    }

    public String getKey() {
        return apikey;
    }
}
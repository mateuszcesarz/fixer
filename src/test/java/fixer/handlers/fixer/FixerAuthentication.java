package fixer.handlers.fixer;

import lombok.SneakyThrows;

import java.util.Properties;

public class FixerAuthentication {
    private final static Properties PROPERTIES = new Properties();
    private final static String KEY = "key";

    @SneakyThrows
    public FixerAuthentication() {
        PROPERTIES.load(getClass().getClassLoader().getResourceAsStream("fixer.properties"));

    }

    public String getKey() {
        return PROPERTIES.getProperty(KEY);
    }
}
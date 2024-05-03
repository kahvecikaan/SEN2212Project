package com.example.expressionevaluator.env;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, Integer> variables = new HashMap<>();

    public void define(String name, int value) {
        variables.put(name, value);
    }

    public boolean isDefined(String name) {
        return variables.containsKey(name);
    }

    public int getValue(String name) {
        return variables.get(name);
    }

}

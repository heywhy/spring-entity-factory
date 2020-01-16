package com.github.heywhy.springentityfactory;

import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.Map;

public class TypeValueGenerator {

    private static Map<Class, Generator> generators = new LinkedHashMap<>();

    public static void add(Class klass, Generator callback) {
        generators.put(klass, callback);
    }

    public static void add(Class[] classes, Generator callback) {
        for (Class klass : classes) {
            add(klass, callback);
        }
    }

    public static Generator get(Class klass) {
        return generators.get(klass);
    }

    public static interface Generator<T> {
        T apply(Faker faker);
    }
}

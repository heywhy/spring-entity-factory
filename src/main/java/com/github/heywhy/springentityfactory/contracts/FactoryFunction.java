package com.github.heywhy.springentityfactory.contracts;

import com.github.javafaker.Faker;

@FunctionalInterface
public interface FactoryFunction<T> {
    T apply(Faker faker);
}

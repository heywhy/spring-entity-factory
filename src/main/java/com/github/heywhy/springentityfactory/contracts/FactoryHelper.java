package com.github.heywhy.springentityfactory.contracts;

import com.github.javafaker.Faker;

public interface FactoryHelper<T> {
    Class<T> getEntity();
    T apply(Faker faker, ModelFactory factory);
}
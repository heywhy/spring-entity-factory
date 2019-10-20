package com.github.heywhy.springentityfactory.contracts;

import java.util.List;
import java.util.function.Function;

public interface ModelFactory {
    <T> T make(Class<T> model);
    <T> T create(Class<T> model);
    <T> List<T> make(Class<T> model, int count);
    <T> List<T> create(Class<T> model, int count);
    <T> EntityFactoryBuilder<T> pipe(Class<T> model);
    <T> void register(FactoryHelper<T> factoryHelper);
    <T> void register(Class<? extends FactoryHelper<T>> factoryHelper);
    <T> ModelFactory define(Class<T> model, FactoryFunction<T> builder);
}

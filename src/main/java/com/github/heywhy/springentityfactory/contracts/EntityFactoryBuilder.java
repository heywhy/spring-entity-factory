package com.github.heywhy.springentityfactory.contracts;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface EntityFactoryBuilder<T> {
    T make();
    T create();
    List<T> make(int count);
    List<T> create(int count);
    EntityFactoryBuilder state(String name);
    EntityFactoryBuilder state(Set<String> states);
    EntityFactoryBuilder<T> then(Function<T, T> ttFunction);
}

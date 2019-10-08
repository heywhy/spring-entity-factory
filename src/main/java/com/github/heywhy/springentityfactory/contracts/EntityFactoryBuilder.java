package com.github.heywhy.springentityfactory.contracts;

import java.util.List;
import java.util.Set;

public interface EntityFactoryBuilder<T> {
    T make();
    T create();
    List<T> make(int count);
    List<T> create(int count);
    EntityFactoryBuilder state(String name);
    EntityFactoryBuilder state(Set<String> states);
}

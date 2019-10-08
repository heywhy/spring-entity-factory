package com.github.heywhy.springentityfactory.impl;

import com.github.javafaker.Faker;
import com.github.heywhy.springentityfactory.contracts.EntityFactoryBuilder;
import com.github.heywhy.springentityfactory.contracts.FactoryFunction;

import javax.persistence.EntityManager;
import java.util.*;

public class EntityFactoryBuilderImpl<T> implements EntityFactoryBuilder<T> {

    private Faker faker;
    private Class<T> tClass;
    private String name = "default";
    private EntityManager entityManager;
    private Map<Class, FactoryFunction> definitions;
    private Set<String> activeStates = new HashSet<>();

    public EntityFactoryBuilderImpl(
            Class<T> tClass,
            Map<Class, FactoryFunction> definitions,
            Faker faker,
            EntityManager entityManager
    ) {
        this.faker = faker;
        this.tClass = tClass;
        this.definitions = definitions;
        this.entityManager = entityManager;
    }

    @Override
    public EntityFactoryBuilder state(String name) {
        activeStates.add(name);
        return this;
    }

    @Override
    public EntityFactoryBuilder state(Set<String> states) {
        activeStates = states;
        return this;
    }

    @Override
    public T create() {
        List<T> instances = create(1);

        return instances.get(0);
    }

    @Override
    public List<T> create(int count) {
        List<T> instances = make(count);
        instances.forEach(entityManager::merge);

        return instances;
    }

    @Override
    public List<T> make(int count) {
        List<T> instances = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            instances.add(make());
        }

        return instances;
    }

    @Override
    public T make() {
        return makeInstance();
    }

    private <A> T makeInstance() {
        if (!definitions.containsKey(tClass)) {
            String msg = "Unable to locate factory with name [" + name + "] [" + tClass.getSimpleName() + "].";
            throw new IllegalArgumentException(msg);
        }

        FactoryFunction<T> function = (FactoryFunction<T>)definitions.get(tClass);
        return function.apply(faker);
    }
}

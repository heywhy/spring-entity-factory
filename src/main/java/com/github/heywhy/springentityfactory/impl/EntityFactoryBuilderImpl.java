package com.github.heywhy.springentityfactory.impl;

import com.github.heywhy.springentityfactory.TypeValueGenerator;
import com.github.javafaker.Faker;
import com.github.heywhy.springentityfactory.contracts.EntityFactoryBuilder;
import com.github.heywhy.springentityfactory.contracts.FactoryFunction;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

public class EntityFactoryBuilderImpl<T extends Object> implements EntityFactoryBuilder<T> {

    private Faker faker;
    private Class<T> tClass;
    private String name = "default";
    private EntityManager entityManager;
    private Function<T, T> operator;
    private Map<Class, FactoryFunction> definitions;
    private Set<String> activeStates = new HashSet<>();

    public EntityFactoryBuilderImpl(
            Class<T> tClass,
            Map<Class, FactoryFunction> definitions,
            Faker faker,
            EntityManager entityManager,
            Function<T, T> operator
    ) {
        this.faker = faker;
        this.tClass = tClass;
        this.operator = operator;
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
        instances.forEach(entityManager::persist);

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
        if (operator != null) {
            return operator.apply(makeInstance(tClass));
        }
        return makeInstance(tClass);
    }

    @Override
    public EntityFactoryBuilder<T> then(Function<T, T> ttFunction) {
        if (operator != null) {
            Function<T, T> op = operator;
            operator = t -> ttFunction.apply(op.apply(t));
        } else {
            operator = ttFunction;
        }
        return this;
    }

    private <A> T makeInstance(Class<T> var) {
        FactoryFunction<T> function = !definitions.containsKey(var)
            ? fakeInstanceFactory(var)
            : (FactoryFunction<T>)definitions.get(var);
        return function.apply(faker);
    }

    private <A> FactoryFunction<T> fakeInstanceFactory(Class<T> var) {
        return (faker) -> {
            try {
                T instance = var.newInstance();
                for (Field field : var.getDeclaredFields()) {
                    boolean isAccessible = field.isAccessible();

                    field.setAccessible(true);
                    field.set(instance, getTypeValue(field.getType()));
                    field.setAccessible(isAccessible);
                }

                return instance;
            } catch (Exception e) {
                e.printStackTrace();

                return null;
            }
        };
    }

    private Object getTypeValue(Class fieldType) {
        TypeValueGenerator.Generator generator = TypeValueGenerator.get(fieldType);
        if (generator != null) {
            return generator.apply(faker);
        }

        return makeInstance(fieldType);
    }
}

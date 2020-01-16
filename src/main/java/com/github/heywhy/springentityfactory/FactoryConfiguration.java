package com.github.heywhy.springentityfactory;

import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.heywhy.springentityfactory.impl.ModelFactoryImpl;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Random;

@ComponentScan
@Configuration
public class FactoryConfiguration {

    @Bean
    public Faker faker() {
        return Faker.instance(new Random());
    }

    @Bean
    public ModelFactory entityFactory(Faker faker, EntityManager entityManager) {
        return new ModelFactoryImpl(faker, entityManager);
    }

    @PostConstruct
    public void initGenerators() {
        TypeValueGenerator.registerDefaults();
    }
}

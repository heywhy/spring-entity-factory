package com.github.heywhy.springentityfactory.impl;

import com.github.heywhy.springentityfactory.contracts.FactoryHelper;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import com.github.javafaker.Faker;
import com.springentityfactory.models.Post;
import com.springentityfactory.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ModelFactoryImplTest {

    private ModelFactory modelFactory;

    @BeforeEach
    void setUp() {
        EntityManager entityManager = Mockito.mock(EntityManager.class);
        EntityTransaction entityTransaction = Mockito.mock(EntityTransaction.class);

        Mockito.when(entityManager.getTransaction())
                .thenReturn(entityTransaction);

        modelFactory = new ModelFactoryImpl(Faker.instance(new Random()), entityManager);

        modelFactory.define(Post.class, faker -> {
            Post post = new Post();

            post.setAuthor(modelFactory.create(User.class));
            post.setTitle(faker.book().title());
            post.setDateCreated(faker.date().birthday());

            return post;
        });

        modelFactory.define(User.class, faker -> {
            User user = new User();

            user.setName(faker.name().fullName());
            user.setUsername(faker.name().username());

            return user;
        });
    }

    @Test
    void testDefineModelFactory() {
        Object user = modelFactory.make(User.class);

        assertNotNull(user);
        assertTrue(user instanceof User);
    }

    @Test
    void testFactoryCreatesDifferentInstance() {
        Object user = modelFactory.make(User.class);
        Object user1 = modelFactory.make(User.class);

        assertNotNull(user);
        assertNotNull(user1);
        assertNotSame(user, user1);
    }

    @Test
    void testCreatePostEntity() {
        Post post = modelFactory.create(Post.class);

        assertNotNull(post);
        assertNotNull(post.getAuthor());
        assertNotNull(post.getDateCreated());
    }

    @Test
    void testCreateNotDefinedEntity() {
        assertThrows(IllegalArgumentException.class, () -> modelFactory.make(App.class));
    }

    @Test
    void testCreateMultipleInstancesOfEntity() {
        List<Post> posts = modelFactory.create(Post.class, 3);

        assertNotNull(posts);
        assertEquals(3, posts.size());
    }

    private static class App {}

    private class UserFactory implements FactoryHelper<User> {
        @Override
        public Class<User> getEntity() {
            return User.class;
        }

        @Override
        public User apply(Faker faker, ModelFactory factory) {
            User user = new User();

            user.setName(faker.name().fullName());
            user.setUsername(faker.name().username());

            return user;
        }
    }
}
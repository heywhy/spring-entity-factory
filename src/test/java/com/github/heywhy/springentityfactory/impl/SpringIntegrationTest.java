package com.github.heywhy.springentityfactory.impl;

import com.github.heywhy.springentityfactory.TestApplication;
import com.github.heywhy.springentityfactory.contracts.ModelFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class})
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class SpringIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelFactory modelFactory;

    @Test
    void testCreateEntityAndPersist() {
//        modelFactory.make()
//        ass
    }

}

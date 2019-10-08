package com.github.heywhy.springentityfactory;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@Import({FactoryConfiguration.class})
public class TestApplication {

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryTest(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPersistenceUnitName("test_unit");
        HibernateJpaDialect hibernateJpaDialect = new HibernateJpaDialect();
        factoryBean.setJpaDialect(hibernateJpaDialect);
        return factoryBean;
    }
}

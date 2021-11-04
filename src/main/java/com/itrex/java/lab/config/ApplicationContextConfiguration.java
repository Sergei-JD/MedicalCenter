package com.itrex.java.lab.config;

import com.itrex.java.lab.service.FlywayService;
import com.itrex.java.lab.util.HibernateUtil;
import com.itrex.java.lab.util.JDBCConnectionPool;
import org.hibernate.Session;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.itrex.java.lab")
public class ApplicationContextConfiguration {

    @Bean
    public DataSource dataSource() {
        return JDBCConnectionPool.INSTANCE.getConnectionPool();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Session session() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public FlywayService flywayService() {
        FlywayService flywayService = new FlywayService("db/test/migration");
        flywayService.migrate();
        return flywayService;
    }

}

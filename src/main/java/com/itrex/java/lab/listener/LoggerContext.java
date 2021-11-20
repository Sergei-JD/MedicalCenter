package com.itrex.java.lab.listener;

import java.util.Arrays;
import java.io.IOException;
import java.util.Properties;
import java.io.FileInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
@Component
@PropertySource("classpath:/application.properties")
public class LoggerContext implements ApplicationListener<ContextRefreshedEvent> {

    private final Environment environment;

    @Value("${logging.config.dev}")
    private String log4jDevPropertiesPath;

    @Value("${logging.config.prod}")
    private String log4jProdPropertiesPath;

    @Autowired
    public LoggerContext(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Properties properties = new Properties();

        if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {

            try (FileInputStream is = new FileInputStream(log4jDevPropertiesPath)) {
                properties.load(is);
                PropertyConfigurator.configure(properties);
                log.info("dev_log4j.properties file received successfully!");
            } catch (IOException ex) {
                log.error("dev_log4j.properties file is not available!");
            }
        }

        if (Arrays.asList(environment.getActiveProfiles()).contains("prod")) {

            try (FileInputStream is = new FileInputStream(log4jProdPropertiesPath)) {
                properties.load(is);
                PropertyConfigurator.configure(properties);
                log.info("prod_log4j.properties file received successfully!");
            } catch (IOException ex) {
                log.error("prod_log4j.properties file is not available!");
            }
        } else {
            log.warn("Not a single configuration file was found that determines the method of logging!");
        }
    }

}
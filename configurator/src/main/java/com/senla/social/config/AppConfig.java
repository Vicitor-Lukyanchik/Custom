package com.senla.social.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class AppConfig {

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException {
        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setDataSource(dataSource());
        managerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        managerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        managerFactoryBean.setJpaProperties(getHibernateProperties());
        managerFactoryBean.setPackagesToScan(env.getRequiredProperty("hibernate.package.to.scan"));
        return managerFactoryBean;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("hibernate.properties"));
            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not find hibernate.properties in classpath");
        }
    }

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getRequiredProperty("hibernate.datasource.driver.class.name"));
        dataSource.setJdbcUrl(env.getRequiredProperty("hibernate.datasource.url"));
        dataSource.setUser(env.getRequiredProperty("hibernate.datasource.username"));
        dataSource.setPassword(env.getRequiredProperty("hibernate.datasource.password"));

        dataSource.setAcquireIncrement(Integer.parseInt(env.getRequiredProperty("pool.acquire.increment")));
        dataSource.setIdleConnectionTestPeriod(Integer.parseInt(env.getRequiredProperty("pool.connection.test.period")));
        dataSource.setInitialPoolSize(Integer.parseInt(env.getRequiredProperty("pool.initial.size")));
        dataSource.setMinPoolSize(Integer.parseInt(env.getRequiredProperty("pool.min.size")));
        dataSource.setMaxPoolSize(Integer.parseInt(env.getRequiredProperty("pool.max.size")));
        dataSource.setMaxIdleTime(Integer.parseInt(env.getRequiredProperty("pool.max.idle.time")));
        dataSource.setMaxStatements(Integer.parseInt(env.getRequiredProperty("pool.max.statement")));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return manager;
    }
}

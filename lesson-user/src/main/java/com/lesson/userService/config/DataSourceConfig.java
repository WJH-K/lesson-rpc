package com.lesson.userService.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by wq on 2018/1/21.
 */
@Configuration
public class DataSourceConfig {
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int runCheck;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;

    @Bean(name = "lessonDataSource")
    @Qualifier("lessonDataSource")
    @ConfigurationProperties(prefix="spring.datasource.lesson")
    public DataSource askconfigDataSource(){
        DataSource dataSource = DataSourceBuilder.create().type(DruidDataSource.class).build();
        return dataSourceConfig(dataSource);
    }

    private DataSource dataSourceConfig(DataSource dataSource) {
        DruidDataSource tmp = (DruidDataSource) dataSource;
        tmp.setMaxActive(maxActive);
        tmp.setMinIdle(minIdle);
        tmp.setInitialSize(initialSize);
        tmp.setTestOnBorrow(false);
        tmp.setTestOnReturn(false);
        tmp.setTimeBetweenEvictionRunsMillis(runCheck);
        tmp.setTestWhileIdle(testWhileIdle);
        tmp.setValidationQuery("select 1");
        return tmp;
    }
}

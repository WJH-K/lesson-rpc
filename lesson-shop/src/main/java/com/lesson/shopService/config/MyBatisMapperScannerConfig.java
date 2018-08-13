package com.lesson.shopService.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis扫描接口
 *
 * @author liuzh
 * @since 2015-12-19 14:46
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
@AutoConfigureAfter(value = {LessonMybatisConfig.class})
public class MyBatisMapperScannerConfig {

    @Bean(name = "lessonMapperScannerConfigurer")
    public MapperScannerConfigurer lessonMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("lessonSqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.lesson.shopService.mapper.lesson");
        return mapperScannerConfigurer;
    }
}

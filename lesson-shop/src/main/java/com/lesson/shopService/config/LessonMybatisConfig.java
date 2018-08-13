package com.lesson.shopService.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis基础配置
 *
 * @author wjh
 * @since 2018-05-28 10:11
 */
@SpringBootApplication
@EnableTransactionManagement // 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
public class LessonMybatisConfig implements TransactionManagementConfigurer {
    @Resource(name = "lessonDataSource")
    private DataSource lessonDataSource;
    @Resource(name = "lessonTransactionManager")
    private PlatformTransactionManager lessonTransactionManager;

    @Bean(name = "lessonSqlSessionFactory")
    public SqlSessionFactory lessonSqlSessionFactoryBean() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(lessonDataSource);
        //设置实体类的包名
        bean.setTypeAliasesPackage("com.lesson.shopService.model.lesson");
        //分页插件
        PageInterceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        //设置数据库类型 Oracle,Mysql,MariaDB,SQLite,Hsqldb,PostgreSQL,sqlserver 7种数据库
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("params", "pageNum=start;pageSize=limit;pageSizeZero=zero;reasonable=heli;count=contsql");

        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/lesson/*.xml"));
            SqlSessionFactory sqlSessionFactory = bean.getObject();
            return sqlSessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "lessonSqlSessionTemplate")
    public SqlSessionTemplate lessonSqlSessionTemplate() {
        return new SqlSessionTemplate(lessonSqlSessionFactoryBean());
    }

    /**
     * 创建事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "lessonTransactionManager")
    public PlatformTransactionManager lessonTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return lessonTransactionManager;
    }
}

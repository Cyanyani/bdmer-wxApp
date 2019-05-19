package com.bdmer.wxapp.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * 开启datasource事务管理, 以及mapper - 配置类
 *
 * @since 2019-04-17 11:45
 * @author gongdelang
 */
@Configuration
@MapperScan(basePackages = {"com.bdmer.wxapp.dao"}, sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceConfiguration.class);

    /**
     *
     * @param dataSource (由springboot自动注入容器)
     * @return bean (由springboot自动注入容器)
     */
    @Bean
    public PlatformTransactionManager producePlatformTransactionManager(DataSource dataSource) {
        LOG.info("dataSource 开启事务成功");
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean f = new SqlSessionFactoryBean();
        f.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));
        f.setDataSource(dataSource);

        SqlSessionFactory fc = f.getObject();

        return fc;
    }
}

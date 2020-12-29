package com.woojin.pochat.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mapstruct.Qualifier;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(basePackages={"com.woojin.pochat.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("com.woojin.pochat.domain.entities");
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/*.xml"));
//        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mapper/mybatis-config.xml"));
        return sqlSessionFactoryBean.getObject();
    }
    @Bean

    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}

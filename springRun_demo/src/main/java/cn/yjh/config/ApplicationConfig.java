package cn.yjh.config;

import cn.yjh.bean.User;
import cn.yjh.dao.UserMapper;
import cn.yjh.spring_mybatis.DataSourceBeanFactoryPostProcessor;
import cn.yjh.spring_mybatis.MapperScans;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@ComponentScan("cn.yjh")
@Configuration
//@MapperScans("cn.yjh.dao")
@MapperScan("cn.yjh.dao")
//@Conditional(ConditionOnBean.class)
//@Import(DataSourceBeanFactoryPostProcessor.class)
public class ApplicationConfig {

    @Bean("user")
    public User setUser(){
        return new User();
    }

    @Bean("dataSource")
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://106.52.128.52:8083/test");
        dataSource.setUsername("root");
        dataSource.setPassword("ZTCokG)j>3wq32@fsreKL");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        return factoryBean.getObject();
    }

   /* @Bean
    public UserMapper userMapper() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
        return sqlSessionTemplate.getMapper(UserMapper.class);
    }*/

    /**
     * ���ÿ����һ�� Mapper ��Ҫע��һ�� MapperFactoryBean �� spring ������
     * ����ʹ���Զ�ɨ�����������ϼ��� @
     * @return
     * @throws Exception
     */
    /*@Bean
    public MapperFactoryBean<UserMapper> userMapper() throws Exception {
        MapperFactoryBean mapperFactoryBean = new MapperFactoryBean(UserMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
        return mapperFactoryBean;
    }*/

}

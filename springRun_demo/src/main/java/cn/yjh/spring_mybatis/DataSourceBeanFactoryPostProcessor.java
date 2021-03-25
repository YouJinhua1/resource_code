package cn.yjh.spring_mybatis;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-20 14:31
 */
public class DataSourceBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://106.52.128.52:8083/test");
        dataSource.setUsername("root");
        dataSource.setPassword("ZTCokG)j>3wq32@fsreKL");


        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        beanFactory.registerSingleton("dataSource",dataSource);
        try {
            beanFactory.registerSingleton("sqlSessionFactory",factoryBean.getObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

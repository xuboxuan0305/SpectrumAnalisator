package SpectrumApp.java.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@ComponentScan(basePackages = {"SpectrumApp.java"})

public class SpringApplicationConfig {

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
//
//    @Bean
//    public static DataSource dataSource(
//            @Value("${jdbc.url}") String jdbcUrl,
//            @Value("${driverClass}") String driverClass,
//            @Value("${database.user.name}") String userName,
//            @Value("${database.user.password}") String password) {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setUrl(jdbcUrl);
//        ds.setDriverClassName(driverClass);
//        ds.setUsername(userName);
//        ds.setPassword(password);
//        return ds;
//    }
//
//    @Bean
//    public Properties hibernateProperties(
//            @Value("${hibernate.dialect}") String dialect,
//            @Value("${hibernate.show_sql}") boolean showSql,
//            @Value("${hibernate.format_sql}") boolean formatSql,
//            @Value("${hibernate.hbm2ddl.auto}") String hbm2ddl) {
//
//        Properties properties = new Properties();
//        properties.put("hibernate.dialect", dialect);
//        properties.put("hibernate.show_sql", showSql);
//        properties.put("hibernate.format_sql", formatSql);
//        properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
//
//        return properties;
//    }
//
//    @Bean
//    public SessionFactory sessionFactory(DataSource dataSource,
//                                         @Value("${hibernate.packagesToScan}") String packagesToScan,
//                                         @Qualifier("hibernateProperties") Properties properties) throws Exception {
//
//        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setPackagesToScan(packagesToScan);
//        sessionFactoryBean.setHibernateProperties(properties);
//        sessionFactoryBean.afterPropertiesSet();
//        return sessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
//        return new HibernateTransactionManager(sessionFactory);
//    }
}



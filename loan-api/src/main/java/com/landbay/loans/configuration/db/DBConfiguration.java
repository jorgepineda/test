package com.landbay.loans.configuration.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages=DBConfiguration.REPOSITORY_PACKAGES_TO_SCAN, includeFilters = @ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE, value={Repository.class}))
public class DBConfiguration {

    public static final String REPOSITORY_PACKAGES_TO_SCAN = "com.landbay.loans";
    public static final String SEPARATOR = ",";
    public static final String ENTITYMANAGER_PACKAGES_TO_SCAN = "com.landbay.loans";
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
    private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
    private static final String PROPERTY_NAME_DATABASE_JPA_VENDOR = "db.database";    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_GENERATE_DDL = "hibernate.generate_ddl";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_DDL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_AUTO_CLOSE_SESSION = "hibernate.auto_close_session";
    private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_USE_UNICODE = "hibernate.connection.useUnicode";
    private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTER_ENCODING = "hibernate.connection.characterEncoding";
    private static final String PROPERTY_NAME_HIBERNATE_CONNECTION_CHAR_SET = "hibernate.connection.charSet";
    private static final String PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS = "hibernate.use_sql_comments";
    private static final String PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS = "hibernate.generate_statistics";

    @Autowired
    private Environment environment;

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource());
        String packagesToScan = ENTITYMANAGER_PACKAGES_TO_SCAN;
        entityManagerFactory.setPackagesToScan(extractPackages(packagesToScan));
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactory.setJpaProperties(loadHibernateProperties());

        return entityManagerFactory;
    }

    protected String[] extractPackages(String packagesToScan) {
        return packagesToScan == null ? new String[]{} : packagesToScan.split(SEPARATOR);
    }

    @Bean
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("db-schema.sql").build();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();

        hibernateJpaVendorAdapter.setShowSql(Boolean.valueOf(environment.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL)));
        hibernateJpaVendorAdapter.setGenerateDdl(Boolean.valueOf(environment.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_DDL)));
        hibernateJpaVendorAdapter.setDatabase(Database.valueOf(environment.getProperty(PROPERTY_NAME_DATABASE_JPA_VENDOR)));

        return hibernateJpaVendorAdapter;
    }

    private Properties loadHibernateProperties() {
        Properties hibernateProperties = new Properties();

        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment.getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_GENERATE_DDL, environment.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_DDL));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_DDL, environment.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_DDL));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_AUTO_CLOSE_SESSION, environment.getProperty(PROPERTY_NAME_HIBERNATE_AUTO_CLOSE_SESSION));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_USE_UNICODE, environment.getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_USE_UNICODE));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTER_ENCODING, environment.getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_CHARACTER_ENCODING));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_CONNECTION_CHAR_SET, environment.getProperty(PROPERTY_NAME_HIBERNATE_CONNECTION_CHAR_SET));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS, environment.getProperty(PROPERTY_NAME_HIBERNATE_USE_SQL_COMMENTS));
        hibernateProperties.put(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS, environment.getProperty(PROPERTY_NAME_HIBERNATE_GENERATE_STATISTICS));
        return hibernateProperties;
    }

}

package pl.lodz.p.it.functionalfood.investigator.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "adminEntityManagerFactory",
        transactionManagerRef = "adminTransactionManager")
@EnableTransactionManagement
public class AdminDataSourceConfig {

    @Value("${spring.functional.food.database.url}")
    private String url;

    @Value("${spring.admin.datasource.username}")
    private String adminDatasourceUsername;

    @Value("${spring.admin.datasource.password}")
    private String adminDatasourcePassword;

    @Value("${spring.admin.datasource.maximum-pool-size}")
    private int adminDatasourceMaxPoolSize;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String DDLAuto;


    @Primary
    @Bean(name = "adminDataSource")
    public DataSource adminDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(adminDatasourceUsername);
        config.setPassword(adminDatasourcePassword);
        config.setMaximumPoolSize(adminDatasourceMaxPoolSize);
        return new HikariDataSource(config);
    }

    @Primary
    @Bean(name = "adminEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean adminDataSourceEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("adminDataSource") DataSource adminDataSource) {

        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(adminDataSource)
                .packages("pl.lodz.p.it.functionalfood.investigator.entities")
                .persistenceUnit("adminPU")
                .build();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", DDLAuto);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean(name = "adminTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("adminEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

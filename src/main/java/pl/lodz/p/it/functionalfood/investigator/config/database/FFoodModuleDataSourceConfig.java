package pl.lodz.p.it.functionalfood.investigator.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "ffoodModuleEntityManagerFactory",
        transactionManagerRef = "ffoodModuleTransactionManager", basePackages = "pl.lodz.p.it.functionalfood.investigator.ffoodmodule.repositories")
@EnableTransactionManagement
public class FFoodModuleDataSourceConfig {

    @Value("${spring.functional.food.database.url}")
    private String url;

    @Value("${spring.ffoodmodule.datasource.username}")
    private String ffoodModuleDatasourceUsername;

    @Value("${spring.ffoodmodule.datasource.password}")
    private String ffoodModuleDatasourcePassword;

    @Bean(name = "ffoodModuleDataSource")
    public DataSource ffoodModuleDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(ffoodModuleDatasourceUsername);
        config.setPassword(ffoodModuleDatasourcePassword);
//        config.setMaximumPoolSize(1);
        return new HikariDataSource(config);
    }

    @Bean(name = "ffoodModuleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ffoodModuleDataSourceEntityManager(
            EntityManagerFactoryBuilder builder, @Qualifier("ffoodModuleDataSource") DataSource ffoodModuleDataSource) {

        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(ffoodModuleDataSource)
                .packages("pl.lodz.p.it.functionalfood.investigator.entities")
                .persistenceUnit("ffoodModulePU")
                .build();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean(name = "ffoodModuleTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("ffoodModuleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

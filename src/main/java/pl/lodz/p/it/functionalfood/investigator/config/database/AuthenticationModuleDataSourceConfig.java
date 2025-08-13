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
@EnableJpaRepositories(entityManagerFactoryRef = "authenticationModuleEntityManagerFactory",
        transactionManagerRef = "authenticationModuleTransactionManager", basePackages = "pl.lodz.p.it.functionalfood.investigator.authenticationmodule.repositories")
@EnableTransactionManagement
public class AuthenticationModuleDataSourceConfig {

    @Value("${spring.functional.food.database.url}")
    private String url;

    @Value("${spring.authenticationmodule.datasource.username}")
    private String authenticationModuleDatasourceUsername;

    @Value("${spring.authenticationmodule.datasource.password}")
    private String authenticationModuleDatasourcePassword;

    @Bean(name = "authenticationModuleDataSource")
    public DataSource accountModuleDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(authenticationModuleDatasourceUsername);
        config.setPassword(authenticationModuleDatasourcePassword);
        config.setMaximumPoolSize(1);
        return new HikariDataSource(config);
    }

    @Bean(name = "authenticationModuleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean accountModuleDataSourceEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("authenticationModuleDataSource") DataSource authenticationDataSource) {

        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(authenticationDataSource)
                .packages("pl.lodz.p.it.functionalfood.investigator.entities")
                .persistenceUnit("authenticationModulePU")
                .build();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean(name = "authenticationModuleTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("authenticationModuleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

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
@EnableJpaRepositories(entityManagerFactoryRef = "accountModuleEntityManagerFactory",
        transactionManagerRef = "accountModuleTransactionManager", basePackages = "pl.lodz.p.it.functionalfood.investigator.accountmodule.repositories")
@EnableTransactionManagement
public class AccountModuleDataSourceConfig {

    @Value("${spring.functional.food.database.url}")
    private String url;
    @Value("${spring.accountmodule.datasource.username}")
    private String accountModuleDatasourceUsername;
    @Value("${spring.accountmodule.datasource.password}")
    private String accountModuleDatasourcePassword;
    @Bean(name = "accountModuleDataSource")
    public DataSource accountModuleDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(accountModuleDatasourceUsername);
        config.setPassword(accountModuleDatasourcePassword);
        config.setMaximumPoolSize(1);
        return new HikariDataSource(config);
    }

    @Bean(name = "accountModuleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean accountModuleDataSourceEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("accountModuleDataSource") DataSource accountModuleDataSource) {

        LocalContainerEntityManagerFactoryBean em = builder
                .dataSource(accountModuleDataSource)
                .packages("pl.lodz.p.it.functionalfood.investigator.entities")
                .persistenceUnit("accountModulePU")
                .build();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean(name = "accountModuleTransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("accountModuleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

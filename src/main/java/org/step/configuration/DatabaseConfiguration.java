package org.step.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.Random;

@Configuration
@ComponentScan(basePackages = {"org.step"})
@PropertySources({
        @PropertySource("classpath:/db.properties")
})
public class DatabaseConfiguration {

    /*
    Стартуер раньше Configuration и создается раньше
     */
    private Environment environment;

    @Bean
//    @Profile(value = {"dev", "test"})
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(
                Optional.ofNullable(environment.getProperty("db.driver"))
                        .orElse("org.postgresql.Driver")
        );
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.user"));
        dataSource.setPassword(environment.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Random random() {
        return new Random();
    }

    /*
    @Autowired - проводим зависимости в бин (Set method, поле, конструктор)
    @Qualifier - разрешаем спорные инжекты разных бинов с одним и тем же именем
     */

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

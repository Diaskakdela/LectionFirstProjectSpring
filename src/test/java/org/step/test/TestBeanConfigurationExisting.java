package org.step.test;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.step.configuration.DatabaseConfiguration;
import org.step.model.User;
import org.step.repository.AuthoritiesRepository;
import org.step.repository.UserRepository;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@ActiveProfiles(value = {"dev", "test"})
public class TestBeanConfigurationExisting {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    private UserRepository<User> userRepository;
    private AuthoritiesRepository<User> authoritiesRepository;

    @Before
    public void setup() {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Test
    public void shouldDatabaseNotBeNull()  {
        Assertions.assertThat(dataSource).isNotNull();
        Assertions.assertThat(jdbcTemplate).isNotNull();
        Assertions.assertThat(userRepository).isNotNull();
        Assertions.assertThat(authoritiesRepository).isNotNull();
    }

    @Autowired
    public void setUserRepository(UserRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthoritiesRepository(AuthoritiesRepository<User> authoritiesRepository) {
        this.authoritiesRepository = authoritiesRepository;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

package org.step.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IdChecker {

    private static final String ID_CHECKER = "SELECT MAX(user_id) FROM users";

    private JdbcTemplate jdbcTemplate;

    public Long getNextId() {
        long maxId;

        maxId = Optional.ofNullable(
                this.jdbcTemplate.queryForObject(ID_CHECKER, Long.class)
        ).orElse(0L);

        return ++maxId;
    }

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
}

package org.step.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.util.IdChecker;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository<User> {

    private static final String REGISTRATION = "insert into users(user_id,username,password) values(?,?,?)";
    private static final String FIND_ALL = "select * from users";
    private static final String FIND_BY_ID = "select * from users where user_id=?";
    private static final String DELETE = "delete from users where user_id=?";
    private static final String UPDATE = "update users set username=?, password=? where user_id=?";
    private static final String UPDATE_ROLE = "insert into authorities(user_id,authorities) values(?,?)";
    private static final String LOGIN = "select * from users where username=:username and password=:password";
    private static final String FIND_AUTHORITIES_BY_ID = "select authorities from authorities where user_id = ?";
    private static final String FIND_BY_USERNAME = "select * from users where username=?";

    private static final String USER_ID = "user_id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private IdChecker idChecker;
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        // Заполнение базы данных
    }

    @PreDestroy
    private void preDestroy() {
        // удаление всего с базы данных
    }

    @Override
    public Optional<User> login(User user) {
//        Map<String, String> parameterMap = new HashMap<>();
//
//        parameterMap.put("username", user.getUsername());
//        parameterMap.put("password", user.getPassword());

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(user);

        return Optional.ofNullable(namedJdbcTemplate.queryForObject(LOGIN, namedParameters, this::mapRow));
    }

    @Override
    public User save(User user) {
        user.setId(idChecker.getNextId());
        this.jdbcTemplate.update(REGISTRATION, user.getId(), user.getUsername(), user.getPassword());
        return user;
    }

    @Override
    public boolean delete(User user) {
        int update = this.jdbcTemplate.update(DELETE, user.getId());
        return update != -1;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                FIND_BY_ID,
                new Object[]{id},
                this::mapRow
        ));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(this.jdbcTemplate.queryForObject(
                FIND_BY_USERNAME,
                new Object[]{username},
                this::mapRow
        ));
    }

    @Override
    public List<User> findAll() {
        return this.jdbcTemplate.query(
                FIND_ALL,
                this::mapRow
        );
    }

    @Override
    public User update(User user) {
        int update = this.jdbcTemplate.update(UPDATE, user.getUsername(), user.getPassword(), user.getId());
        if (update != -1) {
            return user;
        }
        return null;
    }

    @Override
    public boolean saveAuthorities(User user) {
        int update = this.jdbcTemplate.update(UPDATE_ROLE, user.getId(), user.getRole().toString());
        return update != -1;
    }

    @Override
    public Optional<String> findAuthoritiesByUserId(Long id) {
        return Optional.ofNullable(
                this.jdbcTemplate.queryForObject(FIND_AUTHORITIES_BY_ID, new Object[]{id}, String.class)
        );
    }

    private User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getLong(USER_ID));
        user.setUsername(rs.getString(USERNAME));
        user.setPassword(rs.getString(PASSWORD));

        return user;
    }

    @Autowired
    public void setIdChecker(IdChecker idChecker) {
        this.idChecker = idChecker;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}

package ru.yandex.practicum.filmorate.storage.dbimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.util.List;

@Component
@Primary
public class UserDbStorage implements UserStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private FriendsStorage friendsDao;

    @Override
    public User add(User user) {
        final String INSERT_SQL = "insert into users(email, login, name, birthday) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getLogin());
                    ps.setString(3, user.getName());
                    ps.setDate(4, Date.valueOf(user.getBirthday()));
                    return ps;
                },
                keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public void remove(int id) {
        final String DELETE_SQL_FROM_USERS = "DELETE FROM users WHERE id=?";
        jdbcTemplate.update(DELETE_SQL_FROM_USERS, id);
    }

    @Override
    public User update(User user) {
        final String UPDATE_SQL = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ?" +
                "WHERE id=?";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UPDATE_SQL, new String[]{"id"});
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getLogin());
                    ps.setString(3, user.getName());
                    ps.setDate(4, Date.valueOf(user.getBirthday()));
                    ps.setInt(5, user.getId());
                    return ps;
                });
        return user;
    }

    @Override
    public User get(int id) {
        final String SQL_QUERY = "SELECT * FROM users WHERE id=?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_QUERY, this::mapRowToUser, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Пользователь с id = " + id + " не найден");
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        final String SQL_QUERY = "SELECT * FROM users";
        return jdbcTemplate.query(SQL_QUERY, this::mapRowToUser);
    }

    @Override
    public void deleteAll() {
        final String DELETE_SQL = "DELETE FROM users";
        jdbcTemplate.update(DELETE_SQL);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .login(resultSet.getString("login"))
                .name(resultSet.getString("name"))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .friends(friendsDao.getUserFriend(resultSet.getInt("id")))
                .build();
    }
}

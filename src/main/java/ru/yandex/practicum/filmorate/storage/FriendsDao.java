package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
@Primary
public class FriendsDao implements FriendsStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addFriend(int userId, int friendId) {
        final String INSERT_SQL = "insert into friendship(user_id, friend_id) values(?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL);
                    ps.setInt(1, userId);
                    ps.setInt(2, friendId);
                    return ps;
                });
    }

    public void removeFriend(int userId, int friendId) {
        final String DELETE_SQL = "DELETE FROM friendship WHERE user_id = ? and friend_id = ?";
        jdbcTemplate.update(DELETE_SQL, userId, friendId);
    }

    public int mapRowToFriend(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("friend_id");
    }

    public Set<Integer> getUserFriend(int userId) {
        final String SQL_QUERY = "SELECT FRIEND_ID FROM FRIENDSHIP WHERE USER_ID=?";
        return new HashSet<>(jdbcTemplate.query(SQL_QUERY, this::mapRowToFriend, userId));
    }
}

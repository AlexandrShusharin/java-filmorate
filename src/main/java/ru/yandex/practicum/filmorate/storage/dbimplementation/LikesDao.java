package ru.yandex.practicum.filmorate.storage.dbimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Component
@Primary
public class LikesDao implements LikesStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int userId, int filmId) {
        final String INSERT_SQL = "insert into likes(user_id, film_id) values(?,?)";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL);
                    ps.setInt(1, userId);
                    ps.setInt(2, filmId);
                    return ps;
                });
    }

    @Override
    public Set<Integer> getFilmLikes (int filmId) {
        final String SQL_QUERY = "SELECT user_id FROM likes WHERE film_id = ?";
        return new HashSet<>(jdbcTemplate.query(SQL_QUERY, this::mapRowToLike, filmId));
    }

    @Override
    public void removeLike(int userId, int filmId) {
        final String DELETE_SQL_FROM_USERS = "DELETE FROM likes WHERE user_id = ? and film_id = ?";
        jdbcTemplate.update(DELETE_SQL_FROM_USERS, userId, filmId);
    }

    private int mapRowToLike(ResultSet resultSet, int rowNum) throws SQLException {
        return resultSet.getInt("user_id");
    }
}

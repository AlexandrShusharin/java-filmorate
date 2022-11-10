package ru.yandex.practicum.filmorate.storage.dbimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class GenreDbStorage implements GenreStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Genre get(int id) {
        final String SqlQuery = "SELECT * FROM genres WHERE id=?";
        Genre genre;
        try {
            genre = jdbcTemplate.queryForObject(SqlQuery, this::mapRowTopGenre, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Жанр с id = " + id + " не найден");
        }
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        final String SqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(SqlQuery, this::mapRowTopGenre);
    }

    @Override
    public void setFilmGenre(int filmId, int genreId) {
        final String INSERT_SQL = "insert into film_genre(film_id, genre_id) VALUES(?,?)";
        jdbcTemplate.update(INSERT_SQL, filmId, genreId);
    }

    @Override
    public void clearFilmGenre(int filmId) {
        final String DELETE_SQL = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(DELETE_SQL, filmId);
    }

    @Override
    public List<Genre> getGenreByFilmId(int filmId) {
        final String SqlQuery = "SELECT genres.* FROM genres INNER JOIN film_genre " +
                "ON genres.id = film_genre.genre_id " +
                "WHERE film_genre.film_id = ?";
        return jdbcTemplate.query(SqlQuery, this::mapRowTopGenre, filmId);
    }

    @Override
    public void setFilmAllGenre(int filmId, List<Integer> genres) {
        String inSql = String.join(",", Collections.nCopies(genres.size(), "?"));
        String insertSql = String.format("INSERT INTO film_genre(film_id, genre_id) " +
                "SELECT ?, genres.id FROM genres WHERE genres.id IN (%s)", inSql);
        LinkedList<Integer> params = new LinkedList<>(genres);
        params.addFirst(filmId);
        jdbcTemplate.update(insertSql, params.toArray());
    }

    private Genre mapRowTopGenre(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}

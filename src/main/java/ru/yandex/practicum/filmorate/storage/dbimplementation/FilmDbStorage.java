package ru.yandex.practicum.filmorate.storage.dbimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MpaStorage mpaStorage;
    @Autowired
    private GenreStorage genreStorage;
    @Autowired
    private LikesStorage likesDao;

    @Override
    public Film add(Film film) {
        final String INSERT_SQL = "insert into films(name, description, release_date, duration, rate, mpa_id) " +
                "values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                    ps.setString(1, film.getName());
                    ps.setString(2, film.getDescription());
                    ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                    ps.setInt(4, film.getDuration());
                    ps.setInt(5, film.getRate());
                    ps.setInt(6, film.getMpa().getId());
                    return ps;
                },
                keyHolder);
        film.setId(keyHolder.getKey().intValue());
        film.setMpa(mpaStorage.get(film.getMpa().getId()));
        film.setGenres(updateFilmGenres(film));
        return film;
    }

    @Override
    public void remove(int id) {
        final String DELETE_SQL_FROM_USERS = "DELETE FROM films WHERE id=?";
        jdbcTemplate.update(DELETE_SQL_FROM_USERS, id);
    }

    @Override
    public Film update(Film film) {
        final String UPDATE_SQL = "UPDATE films SET name=?, description=?, release_date=?, duration=?, rate=?, mpa_id=?" +
                " WHERE id=?";
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(UPDATE_SQL, new String[]{"id"});
                    ps.setString(1, film.getName());
                    ps.setString(2, film.getDescription());
                    ps.setDate(3, Date.valueOf(film.getReleaseDate()));
                    ps.setInt(4, film.getDuration());
                    ps.setInt(5, film.getRate());
                    ps.setInt(6, film.getMpa().getId());
                    ps.setInt(7, film.getId());
                    return ps;
                });
        film.setMpa(mpaStorage.get(film.getMpa().getId()));
        film.setGenres(updateFilmGenres(film));
        return film;
    }

    @Override
    public Film get(int id) {
        final String SQL_QUERY = "SELECT * FROM films WHERE id=?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(SQL_QUERY, this::mapRowToFilm, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Пользователь с id = " + id + " не найден");
        }
        return film;
    }

    @Override
    public List<Film> getAll() {
        final String SQL_QUERY = "SELECT * FROM films";
        return jdbcTemplate.query(SQL_QUERY, this::mapRowToFilm);
    }

    @Override
    public void deleteAll() {
        final String DELETE_SQL = "DELETE FROM films";
        jdbcTemplate.update(DELETE_SQL);
    }

    private List<Genre> updateFilmGenres(Film film) {
        genreStorage.clearFilmGenre(film.getId());
        if (film.getGenres() != null) {
            List<Integer> genres = film.getGenres().stream().map(Genre::getId).distinct().collect(Collectors.toList());
            genreStorage.setFilmAllGenre(film.getId(), genres);
        }
        return genreStorage.getGenreByFilmId(film.getId());
    }

    private Film mapRowToFilm(ResultSet resultSet, int rowNum) throws SQLException {
        return Film.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .releaseDate(resultSet.getDate("release_date").toLocalDate())
                .duration(resultSet.getInt("duration"))
                .rate(resultSet.getInt("rate"))
                .mpa(mpaStorage.get(resultSet.getInt("mpa_id")))
                .genres(genreStorage.getGenreByFilmId(resultSet.getInt("id")))
                .likes(likesDao.getFilmLikes(resultSet.getInt("id")))
                .build();
    }
}

package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaDbStorage implements MpaStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Mpa get(int id) {
        String SqlQuery = "SELECT * FROM mpa WHERE id=?";
        Mpa mpa;
        try {
            mpa= jdbcTemplate.queryForObject(SqlQuery,this::mapRowTopMpa, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Жанр с id = " + id + " не найден");
        }
        return mpa;
    }

    @Override
    public List<Mpa> getAll() {
        String SqlQuery = "SELECT * FROM mpa";
        return jdbcTemplate.query(SqlQuery,this::mapRowTopMpa);
    }

    private Mpa mapRowTopMpa(ResultSet resultSet, int rowNum) throws SQLException {
        return Mpa.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
    }

    @Override
    public int add(Mpa mpa) {
        return 0;
    }

    @Override
    public void remove(int mpaId) {

    }

    @Override
    public void update(Mpa mpa) {

    }
}

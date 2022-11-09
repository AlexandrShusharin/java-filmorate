package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static int filmIdCounter = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();

    @Override
    public Film add(Film film) {
        film.setId(getNewId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void remove(int id) {
        films.remove(id);
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film get(int id) {
        return films.get(id);
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public void deleteAll() {
        films.clear();
    }

    private int getNewId() {
        return ++filmIdCounter;
    }

    public static void setFilmIdCounter(int filmIdCounter) {
        InMemoryFilmStorage.filmIdCounter = filmIdCounter;
    }
}

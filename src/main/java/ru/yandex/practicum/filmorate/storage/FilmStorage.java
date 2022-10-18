package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    void add(Film film);

    void remove(int id);

    void update(Film film);

    Film get(int id);

    List<Film> getAll();

    void deleteAll();
}

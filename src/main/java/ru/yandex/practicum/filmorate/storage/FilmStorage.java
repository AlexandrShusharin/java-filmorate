package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film add(Film film);

    void remove(int id);

    Film update(Film film);

    Film get(int id);

    List<Film> getAll();

    void deleteAll();
}

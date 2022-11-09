package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    int add(Genre genre);

    void remove (int genreId);

    void update (Genre genre);

    Genre get(int id);

    List<Genre> getAll ();

    List<Genre> getGenreByFilmId (int filmId);

    void setFilmGenre (int filmId, int genreId);

    void clearFilmGenre (int filmId);
}

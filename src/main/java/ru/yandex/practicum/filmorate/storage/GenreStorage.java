package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    Genre get(int id);

    List<Genre> getAll ();

    List<Genre> getGenreByFilmId (int filmId);

    void setFilmGenre (int filmId, int genreId);

    void setFilmAllGenre (int filmId, List<Integer> genres);

    void clearFilmGenre (int filmId);
}

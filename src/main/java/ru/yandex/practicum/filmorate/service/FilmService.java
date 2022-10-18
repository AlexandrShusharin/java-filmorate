package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private FilmStorage filmStorage;
    private UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film addFilm(Film film) {
        filmStorage.add(film);
        return film;
    }

    public Film updateFilm(Film film) {
        filmStorage.update(film);
        return film;
    }

    public void removeFilm(int id) {
        filmStorage.remove(id);
    }

    public Film getFilm(int id) {
        return filmStorage.get(id);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public void addLike(int filmId, int userId) {
        filmStorage.get(filmId).addLike(userId);
    }

    public void removeLike(int filmId, int userId) throws ObjectNotFoundException {
        if (userStorage.get(userId) != null) {
            filmStorage.get(filmId).removeLike(userId);
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    public List<Film> getPopularFilms(int count) {
        System.out.println(count);
        List<Film> popularFilms = filmStorage.getAll().
                stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(),f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
        System.out.println(popularFilms.size());
        return popularFilms;
    }

}

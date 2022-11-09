package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesDao;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private LikesDao likesDao;

    public Film addFilm(Film film) {
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.update(film);
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
        likesDao.addLike(userId, filmId);
    }

    public void removeLike(int filmId, int userId) throws ObjectNotFoundException {
        likesDao.removeLike(userId, filmId);
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> films =  filmStorage.getAll().
                stream()
                .sorted((f1, f2) -> Integer.compare(f2.getLikes().size(), f1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
        return films;
    }

}

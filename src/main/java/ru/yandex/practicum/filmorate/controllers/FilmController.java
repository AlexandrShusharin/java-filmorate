package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
public class FilmController {
    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> getAll() {
        log.info("Получен запрос на список фильмов.");
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getAll(@PathVariable String id) {
        log.info("Получен запрос на фильм id = " + id);
        if (filmService.getFilm(Integer.parseInt(id)) != null) {
            return filmService.getFilm(Integer.parseInt(id));
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }

    @GetMapping("/films/{id}/likes")
    public Set<Integer> getLikes(@PathVariable String id) {
        log.info("Получен запрос на лайки к фильму фильм id = " + id);
        if (filmService.getFilm(Integer.parseInt(id)) != null) {
            return filmService.getFilm(Integer.parseInt(id)).getLikes();
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(@RequestParam(defaultValue = "10") String count) {
        log.info("Получен запрос на первые " + count + " популярных фильмов");
        return filmService.getPopularFilms(Integer.parseInt(count));
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма.");
        return filmService.addFilm(film);
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        log.info("Получен запрос на обновление фильма.");
        if (filmService.getFilm(film.getId()) != null) {
            return filmService.updateFilm(film);
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }

    @PutMapping(value = "films/{id}/like/{userId}")
    public void addLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен запрос на добавление лайка к фильму id = " +  id + " ползователем  id = " + userId);
        if (filmService.getFilm(Integer.parseInt(id)) != null) {
            filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }

    @DeleteMapping(value = "films/{id}/")
    public void remove(@PathVariable String id) {
        log.info("Получен запрос на удаление фильма id = " +  id);
        if (filmService.getFilm(Integer.parseInt(id)) != null) {
            filmService.removeFilm(Integer.parseInt(id));
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }

    @DeleteMapping(value = "films/{id}/like/{userId}")
    public void deleteLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Получен запрос на удаление лайка к фильма id = " +  id + " ползователем  id = " + userId);
        if (filmService.getFilm(Integer.parseInt(id)) != null) {
            filmService.removeLike(Integer.parseInt(id), Integer.parseInt(userId));
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
    }
}

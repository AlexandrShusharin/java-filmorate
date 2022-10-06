package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    static private final String FILMS_START_DATE = "28.12.1895";
    private static int filmIdCounter = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();


    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Получен запрос на список фильмов.");
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        log.info("Получен запрос на добавление фильма.");
        if (testFilmDate(film.getReleaseDate())) {
            film.setId(getNewId());
            films.put(film.getId(), film);
        } else {
            throw new ValidationException("Неверная дата создания фильма");
        }
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) throws ValidationException, ObjectNotFoundException {
        log.info("Получен запрос на обновление фильма.");
        if (films.containsKey(film.getId())) {
            if (testFilmDate(film.getReleaseDate())) {
                films.put(film.getId(), film);
            } else {
                throw new ValidationException("Неверная дата создания фильма");
            }
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
        return film;
    }

    private boolean testFilmDate(LocalDate filmDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate filmsStartDate = LocalDate.parse(FILMS_START_DATE, formatter);
        return filmDate.isAfter(filmsStartDate);
    }

    private int getNewId() {
        return ++filmIdCounter;
    }
}

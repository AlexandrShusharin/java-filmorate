package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    private static int filmIdCounter = 0;
    private final HashMap<Integer, Film> films = new HashMap<>();


    @GetMapping("/films")
    public List<Film> getAll() {
        log.info("Получен запрос на список фильмов.");
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        log.info("Получен запрос на добавление фильма.");
        film.setId(getNewId());
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) throws ObjectNotFoundException {
        log.info("Получен запрос на обновление фильма.");
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
        } else {
            throw new ObjectNotFoundException("Фильма с таким id не существует");
        }
        return film;
    }

    private int getNewId() {
        return ++filmIdCounter;
    }
}

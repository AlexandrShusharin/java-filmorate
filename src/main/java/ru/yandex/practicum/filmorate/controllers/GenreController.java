package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public Optional<List<Genre>> getAll() {
        log.info("Получен запрос на список жанров фильмов.");
        return Optional.ofNullable(genreService.getAllGenres());
    }

    @GetMapping("/genres/{id}")
    public Optional<Genre> getGenre(@PathVariable String id) {
        log.info("Получен запрос на жанр id = " + id);
        if (genreService.getGenre(Integer.parseInt(id)) != null) {
            return Optional.ofNullable(genreService.getGenre(Integer.parseInt(id)));
        } else {
            throw new ObjectNotFoundException("Жанра фильма с таким id не существует");
        }
    }

}

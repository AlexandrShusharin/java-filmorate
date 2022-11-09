package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class MpaController {
    @Autowired
    private MpaService mpaService;

    @GetMapping("/mpa")
    public Optional<List<Mpa>> getAll() {
        log.info("Получен запрос на список возрастного рейтинга фильмов.");
        return Optional.ofNullable(mpaService.getAllMpa());
    }

    @GetMapping("/mpa/{id}")
    public Optional<Mpa> getMPA(@PathVariable String id) {
        log.info("Получен запрос на МРА id = " + id);
        if (mpaService.getMpa(Integer.parseInt(id)) != null) {
            return Optional.ofNullable(mpaService.getMpa(Integer.parseInt(id)));
        } else {
            throw new ObjectNotFoundException("MPA с таким id не существует");
        }
    }
}

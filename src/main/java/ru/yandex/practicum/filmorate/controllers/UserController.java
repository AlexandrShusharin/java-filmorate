package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private static int userIdCounter = 0;

    @GetMapping("/users")
    public List<User> getAll() {
        log.info("Получен запрос на список пользователей.");
        return new ArrayList<>(users.values());
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя.");
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getNewId());
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) throws ObjectNotFoundException {
        log.info("Получен запрос на обновление пользователя.");
        if (users.containsKey(user.getId())) {
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            users.put(user.getId(), user);
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
        return user;
    }

    private int getNewId() {
        return ++userIdCounter;
    }
}

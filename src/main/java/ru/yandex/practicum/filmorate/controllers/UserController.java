package ru.yandex.practicum.filmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Optional<List<User>> getAll() {
        log.info("Получен запрос на список пользователей.");
        return Optional.ofNullable(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable String id) {
        log.info("Получен запрос на пользователя id = " + id);
        if (userService.getUser(Integer.parseInt(id)) != null) {
            return Optional.ofNullable(userService.getUser(Integer.parseInt(id)));
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    @GetMapping("/users/{id}/friends")
    public Optional<List<User>> getFriends(@PathVariable String id) {
        log.info("Получен запрос на друзей пользователя id = " + id);
        if (userService.getUser(Integer.parseInt(id)) != null) {
            return Optional.ofNullable(userService.getAllFriends(Integer.parseInt(id)));
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    @GetMapping("users/{id}/friends/common/{otherId}")
    public Optional<List<User>> getCommonFriends(@PathVariable String id, @PathVariable String otherId) {
        log.info("Получен запрос на общих друзей пользователя id = " + id + " и id = " + otherId);
        if (userService.getUser(Integer.parseInt(id)) != null &&
                userService.getUser(Integer.parseInt(otherId)) != null) {
            return Optional.ofNullable(userService.getCommonFriends(Integer.parseInt(id), Integer.parseInt(otherId)));
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Получен запрос на добавление пользователя.");
        return userService.addUser(user);
    }

    @PutMapping(value = "/users")
    public User update(@Valid @RequestBody User user) {
        log.info("Получен запрос на обновление пользователя id = " + user.getId());
        if (userService.getUser(user.getId()) != null) {
            return userService.updateUser(user);
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Получен запрос на добавление пользователю id = " + id + " друга id = " + friendId);
        if (userService.getUser(Integer.parseInt(id)) != null &&
                userService.getUser(Integer.parseInt(friendId)) != null) {
            userService.addFriend(Integer.parseInt(id), Integer.parseInt(friendId));
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        log.info("Получен запрос на удаление пользователя id = " + id);
        if (userService.getUser(Integer.parseInt(id)) != null) {
            userService.removeUser(Integer.parseInt(id));
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable String id, @PathVariable String friendId) {
        log.info("Получен запрос на удаление у пользователя id = " + id + "друга id = " + friendId);
        if (userService.getUser(Integer.parseInt(id)) != null &&
                userService.getUser(Integer.parseInt(friendId)) != null) {
            userService.removeFriend(Integer.parseInt(id), Integer.parseInt(friendId));
        } else {
            throw new ObjectNotFoundException("Пользователя с таким id не существует");
        }
    }
}


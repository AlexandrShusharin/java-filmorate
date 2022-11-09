package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User add(User user);

    void remove(int id);

    User update(User user);

    User get(int id);

    List<User> getAll();

    void deleteAll();
}

package ru.yandex.practicum.filmorate.storage.inmemimplementation;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final HashMap<Integer, User> users = new HashMap<>();
    private static int userIdCounter = 0;

    @Override
    public User add(User user) {
        user.setId(getNewId());
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void remove(int id) {
        users.remove(id);
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        return users.get(id);
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    private int getNewId() {
        return ++userIdCounter;
    }
}

package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsDao;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserStorage userStorage;
    @Autowired
    private FriendsDao friendsDao;

    public User addUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.add(user);
        return user;
    }

    public User updateUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        userStorage.update(user);
        return user;
    }

    public void removeUser(int id) {
        userStorage.remove(id);

    }

    public User getUser(int id) {
        return userStorage.get(id);
    }

    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public List<User> getAllFriends(int userId) {
        Set<Integer> friendsId = userStorage.get(userId).getFriends();
        List<User> friends = new ArrayList<>();
        if (friendsId.size() > 0) {
            friends = userStorage.getAll().stream()
                    .filter(p -> friendsId.contains(p.getId()))
                    .collect(Collectors.toList());
        }
        return friends;
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        Set<Integer> userFriends = userStorage.get(userId).getFriends();
        Set<Integer> otherUserFriends = userStorage.get(otherId).getFriends();
        List<User> commonFriends = new ArrayList<>();
        if (userFriends.size() > 0 && otherUserFriends.size() > 0) {
            commonFriends = (userStorage.getAll().stream()
                    .filter(p -> userFriends.contains(p.getId()))
                    .filter(p -> otherUserFriends.contains(p.getId()))
                    .collect(Collectors.toList()));
        }
        return commonFriends;
    }

    public void addFriend(int userId, int friendId) {
        friendsDao.addFriend(userId, friendId);
    }

    public void removeFriend(int userId, int friendId) {
        friendsDao.removeFriend(userId, friendId);
    }
}

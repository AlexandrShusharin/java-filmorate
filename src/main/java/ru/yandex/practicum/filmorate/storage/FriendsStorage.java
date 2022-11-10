package ru.yandex.practicum.filmorate.storage;

import java.util.Set;

public interface FriendsStorage {

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Set<Integer> getUserFriend (int userId);

}

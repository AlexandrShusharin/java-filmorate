package ru.yandex.practicum.filmorate.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public interface FriendsStorage {

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Set<Integer> getUserFriend (int userId);

}

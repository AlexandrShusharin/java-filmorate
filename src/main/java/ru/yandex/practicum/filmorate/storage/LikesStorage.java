package ru.yandex.practicum.filmorate.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public interface LikesStorage {

    void addLike(int userId, int filmId);

    void removeLike(int userId, int filmId);

    Set<Integer> getFilmLikes (int filmId);
}

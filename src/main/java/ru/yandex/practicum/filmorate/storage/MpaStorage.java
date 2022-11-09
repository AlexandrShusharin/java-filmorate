package ru.yandex.practicum.filmorate.storage;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {
    int add(Mpa mpa);

    void remove (int mpaId);

    void update (Mpa mpa);

    Mpa get(int id);

    List<Mpa> getAll ();
}

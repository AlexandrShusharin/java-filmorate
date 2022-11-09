package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Service
public class MpaService {
    @Autowired
    private MpaStorage mpaStorage;

    public Mpa getMpa(int id) {
        return mpaStorage.get(id);
    }

    public List<Mpa> getAllMpa() {
        return mpaStorage.getAll();
    }
}

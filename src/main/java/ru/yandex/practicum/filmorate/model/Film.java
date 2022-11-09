package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @Size(max = 200, message = "Описание не можете быть больш 200 символов.")
    private String description;
    @Past(message = "Фильм не может быть из будущего")
    @FilmReleaseDate(message = "Некорректная дата релиза фильма.")
    private LocalDate releaseDate;
    @Positive(message = "Длительность фильма должна быть больше 0")
    private int duration;
    private int rate;
    @NotNull
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();
    private Set<Integer> likes = new HashSet<>();

    public void addLike(Integer userId) {
        likes.add(userId);
    }

    public void removeLike(Integer userId) {
        likes.remove(userId);
    }
}

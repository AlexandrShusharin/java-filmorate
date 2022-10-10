package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private long duration;
}

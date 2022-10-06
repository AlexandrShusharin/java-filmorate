package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email (message = "Не правильно указана электронная почта")
    private String email;
    @NotBlank (message = "Логин  не может быть пустым")
    private String login;
    private String name;
    @Past (message = "Не верно указана дата рождения")
    private LocalDate birthday;
}

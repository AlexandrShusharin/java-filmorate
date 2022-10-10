package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.UserLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    @Email(message = "Не правильно указана электронная почта")
    private String email;
    @NotBlank(message = "Логин  не может быть пустым")
    @UserLogin(message = "Логин не может содержать пробелы")
    private String login;
    private String name;
    @Past(message = "Не верно указана дата рождения")
    private LocalDate birthday;
}

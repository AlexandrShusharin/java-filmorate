package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.validators.UserLogin;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Integer> friends = new HashSet<>();

    public void addFriend(Integer userId) {
        friends.add(userId);
    }

    public void removeFriend(Integer userId) {
        friends.remove(userId);
    }
}

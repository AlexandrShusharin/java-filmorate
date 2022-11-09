package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.name=myapp-test-h2",
        "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus-user"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    @Autowired
    private UserStorage userStorage;

    @Test
    public void testGetAndCrudUser() {

        User testUser = User.builder()
                .email("mail@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.now().minusMonths(3))
                .build();

        Optional<User> userOptional = Optional.ofNullable(userStorage.add(testUser));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );

        userOptional = Optional.ofNullable(userStorage.get(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("id", 1)
                );
        testUser = userStorage.get(1);
        testUser.setName("alex");
        userStorage.update(testUser);
        userOptional = Optional.ofNullable(userStorage.get(1));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("name", "alex")
                );

        Optional<List<User>> optionalUsers = Optional.ofNullable(userStorage.getAll());
        assertThat(optionalUsers)
                .isPresent()
                .hasValueSatisfying(users -> assertThat(users).hasSize(1)
                );

        userStorage.remove(1);
        optionalUsers = Optional.ofNullable(userStorage.getAll());
        assertThat(optionalUsers)
                .isPresent()
                .hasValueSatisfying(users -> assertThat(users).hasSize(0)
                );
    }
}
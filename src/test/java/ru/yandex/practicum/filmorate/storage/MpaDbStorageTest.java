package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.name=myapp-test-h2",
        "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus-mpa"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {
    @Autowired
    private MpaStorage mpaStorage;

    @Test
    public void testGetAndCrudMpa() {

        Optional<Mpa> mpaOptional = Optional.ofNullable(mpaStorage.get(1));
        assertThat(mpaOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1)
                );

        Optional<List<Mpa>> mpasOptional = Optional.ofNullable(mpaStorage.getAll());
        assertThat(mpasOptional)
                .isPresent()
                .hasValueSatisfying(genres ->
                        assertThat(genres).hasSize(5)
                );
    }

}
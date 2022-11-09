package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.name=myapp-test-h2",
        "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus-film"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageTest {
    @Autowired
    private FilmStorage filmStorage;

    @Test
    public void testGetAndCrudFilm() {

        Film testFilm = Film.builder()
                .name("labore nulla")
                .releaseDate(LocalDate.now().minusMonths(3))
                .description("Duis in consequat esse")
                .duration(100)
                .rate(4)
                .mpa(Mpa.builder().id(1).build())
                .build();

        Optional<Film> filmOptional = Optional.ofNullable(filmStorage.add(testFilm));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );

        filmOptional = Optional.ofNullable(filmStorage.get(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("id", 1)
                );
        testFilm = filmStorage.get(1);
        testFilm.setDuration(90);
        filmStorage.update(testFilm);
        filmOptional = Optional.ofNullable(filmStorage.get(1));

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film).hasFieldOrPropertyWithValue("duration", 90)
                );

        Optional<List<Film>> optionalFilms = Optional.ofNullable(filmStorage.getAll());
        assertThat(optionalFilms)
                .isPresent()
                .hasValueSatisfying(films -> assertThat(films).hasSize(1)
                );

        filmStorage.remove(1);
        optionalFilms = Optional.ofNullable(filmStorage.getAll());
        assertThat(optionalFilms)
                .isPresent()
                .hasValueSatisfying(films -> assertThat(films).hasSize(0)
                );
    }
}
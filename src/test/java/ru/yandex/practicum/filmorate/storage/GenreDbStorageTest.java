package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.name=myapp-test-h2",
        "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus-genre"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {
    @Autowired
    private GenreStorage genreStorage;
    @Autowired
    private FilmStorage filmStorage;

    @Test
    public void testGetAndCrudGenre() {

        Film testFilm = Film.builder()
                .name("labore nulla")
                .releaseDate(LocalDate.now().minusMonths(3))
                .description("Duis in consequat esse")
                .duration(100)
                .rate(4)
                .mpa(Mpa.builder().id(1).build())
                .build();
        filmStorage.add(testFilm);

        Optional<Genre> genreOptional = Optional.ofNullable(genreStorage.get(1));
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre ->
                        assertThat(genre).hasFieldOrPropertyWithValue("id", 1)
                );

        genreStorage.setFilmGenre(1,1);
        Optional<List<Genre>> genresOptional = Optional.ofNullable(genreStorage.getGenreByFilmId(1));
        assertThat(genresOptional)
                .isPresent()
                .hasValueSatisfying(genres ->
                        assertThat(genres).hasSize(1)
                );

        genreStorage.clearFilmGenre(1);
        genresOptional = Optional.ofNullable(genreStorage.getGenreByFilmId(1));
        assertThat(genresOptional)
                .isPresent()
                .hasValueSatisfying(genres ->
                        assertThat(genres).hasSize(0)
                );

        genresOptional = Optional.ofNullable(genreStorage.getAll());
        assertThat(genresOptional)
                .isPresent()
                .hasValueSatisfying(genres ->
                        assertThat(genres).hasSize(6)
                );
    }
}
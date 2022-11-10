package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.name=myapp-test-h2",
        "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus-like"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class LikesDaoTest {
    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserStorage userStorage;
    @Autowired
    private LikesStorage likeDao;

    @Test
    public void testGetAndCrudLikes() {

        Film testFilm = Film.builder()
                .name("labore nulla")
                .releaseDate(LocalDate.now().minusMonths(3))
                .description("Duis in consequat esse")
                .duration(100)
                .rate(4)
                .mpa(Mpa.builder().id(1).build())
                .build();
        filmStorage.add(testFilm);

        User testUser = User.builder()
                .email("mail@mail.ru")
                .login("login")
                .name("name")
                .birthday(LocalDate.now().minusMonths(3))
                .build();
        userStorage.add(testUser);

        likeDao.addLike(1,1);

        Optional<Set<Integer>> optionalLikes = Optional.ofNullable(likeDao.getFilmLikes(1));
        assertThat(optionalLikes)
                .isPresent()
                .hasValueSatisfying(likes -> assertThat(likes).hasSize(1)
                );

        likeDao.removeLike(1,1);

        optionalLikes = Optional.ofNullable(likeDao.getFilmLikes(1));
        assertThat(optionalLikes)
                .isPresent()
                .hasValueSatisfying(likes -> assertThat(likes).hasSize(0)
                );

    }
}
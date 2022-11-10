package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.config.name=myapp-test-h2",
        "myapp.trx.datasource.url=jdbc:h2:mem:trxServiceStatus-friend"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FriendsDaoTest {
        @Autowired
        private UserStorage userStorage;
        @Autowired
        private FriendsStorage friendDao;

        @Test
        public void testGetAndCrudFriend() {

            User testUser1 = User.builder()
                    .email("mail@mail.ru")
                    .login("login")
                    .name("name")
                    .birthday(LocalDate.now().minusMonths(3))
                    .build();
            userStorage.add(testUser1);

            User testUser2 = User.builder()
                    .email("mail@mail.ru")
                    .login("login1")
                    .name("name1")
                    .birthday(LocalDate.now().minusMonths(3))
                    .build();
            userStorage.add(testUser2);

            friendDao.addFriend(1,2);

            Optional<Set<Integer>> optionalFriends = Optional.ofNullable(friendDao.getUserFriend(1));
            assertThat(optionalFriends)
                    .isPresent()
                    .hasValueSatisfying(friends ->
                            assertThat(friends).hasSize(1)
                    );
            friendDao.removeFriend(1,2);
            optionalFriends = Optional.ofNullable(friendDao.getUserFriend(1));
            assertThat(optionalFriends)
                    .isPresent()
                    .hasValueSatisfying(friends ->
                            assertThat(friends).hasSize(0)
                    );
        }
}
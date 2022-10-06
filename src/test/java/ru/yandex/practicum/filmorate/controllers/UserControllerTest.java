package ru.yandex.practicum.filmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc


class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void test() {
        String validUser = "{\"id\":null,\"email\":\"user@email.ru\",\"login\":\"login\",\"name\":\"vasya\"" +
                ",\"birthday\":\"2015-03-01\"}";
        String validUserWithId = "{\"id\":1,\"email\":\"user@email.ru\",\"login\":\"login\",\"name\":\"vasya\"" +
                ",\"birthday\":\"2015-03-01\"}";
        String notExistUserWithId = "{\"id\":2,\"email\":\"user@email.ru\",\"login\":\"login\",\"name\":\"vasya\"" +
                ",\"birthday\":\"2015-03-01\"}";
        String wrongBithdayUser = "{\"id\":null,\"email\":\"user@email.ru\",\"login\":\"login\",\"name\":\"vasya\"" +
                ",\"birthday\":\"2022-12-01\"}";

        mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(validUser)).
                andExpect(status().isOk()
                );
       mockMvc.perform(
                        put("/users")
                                .contentType("application/json")
                                .content(validUserWithId)).
                andExpect(status().isOk()
                );
       mockMvc.perform(
                        put("/users")
                                .contentType("application/json")
                                .content(notExistUserWithId)).
                andExpect(status().isNotFound()
                );

        mockMvc.perform(
                        post("/users")
                                .contentType("application/json")
                                .content(wrongBithdayUser)).
                andExpect(status().isBadRequest()
                );
        mockMvc.perform(
                        get("/films")
                                .contentType("application/json")).
                andExpect(status().isOk()
                );
    }
}
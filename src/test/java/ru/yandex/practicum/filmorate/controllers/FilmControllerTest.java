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
class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    void test() {
        String validFilm = "{\"id\":null,\"name\":\"Film Name\",\"description\":\"Film description\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        String validFilmWithId = "{\"id\":1,\"name\":\"Film Name\",\"description\":\"Film description\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        String notExistFilmWithId = "{\"id\":2,\"name\":\"Film Name\",\"description\":\"Film description\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        String noNameFilm = "{\"id\":null,\"name\":\"\",\"description\":\"Film description\",\"releaseDate\":\"2015-03-01\",\"duration\":100}";
        String noValidDateFilm = "{\"id\":null,\"name\":\"Film Name\",\"description\":\"Film description\",\"releaseDate\":\"1800-03-01\",\"duration\":100}";

        mockMvc.perform(
                        post("/films")
                                .contentType("application/json")
                                .content(validFilm)).
                andExpect(status().isOk()
                );
        mockMvc.perform(
                        put("/films")
                                .contentType("application/json")
                                .content(validFilmWithId)).
                andExpect(status().isOk()
                );
        mockMvc.perform(
                        put("/films")
                                .contentType("application/json")
                                .content(notExistFilmWithId)).
                andExpect(status().isNotFound()
                );

        mockMvc.perform(
                        post("/films")
                                .contentType("application/json")
                                .content(noNameFilm)).
                andExpect(status().isBadRequest()
                );
        mockMvc.perform(
                        get("/films")
                                .contentType("application/json")).
                andExpect(status().isOk()
                );
        mockMvc.perform(
                        post("/films")
                                .contentType("application/json")
                                .content(noValidDateFilm)).
                andExpect(status().isBadRequest()
                );
    }
}
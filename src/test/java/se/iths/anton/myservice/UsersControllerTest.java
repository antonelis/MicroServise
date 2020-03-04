package se.iths.anton.myservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(UsersController.class)
@Slf4j
@Import({UsersModelAssembler.class})
class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UsersRepository repository;
    @BeforeEach
    void setup(){

        when(repository.findAll()).thenReturn(List.of(new User(1, "Anton", "Anton Johansson", "Mölndal", 10000, true),
                new User(2,"Sebbe", "Sebastian Waltilla", "Fjärås", 60000, true),
                new User(3,"Jonte", "Jonathan Holm", "Umeå", 40000, false)));
        when(repository.findById(1)).thenReturn(Optional.of(new User(1, "Anton", "Anton Johansson", "Mölndal", 10000, true)));
        when(repository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            var u = (User) args[0];
            return new User(1, u.getUserName(),u.getRealName(),u.getCity(),u.getIncome(),u.isInRelation());
        });
    }

    @Test
    @DisplayName("Calls GET method with url /api/v1/users/1")
    void getOnePersonWithValidIdOne() throws Exception {
        mockMvc.perform(
                get("/api/v1/users/1").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/users/1")));
    }
    @Test
    @DisplayName("Calls GET method with url /api/v1/users/")
    void getAllPersons() throws Exception {
        mockMvc.perform(
                get("/api/v1/users").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/users")));
    }

    @Test
    @DisplayName("Calls POST method with url /api/v1/users/")
    void postOnePersonWithNewId() throws Exception {
        mockMvc.perform(
                post("/api/v1/users/").contentType("application/json").content("{\"id\":0,\"userName\":\"Innan JONANNA\",\"realName\":\"Johanna Svallingson\",\"city\":\"goteborg\",\"income\":10000,\"inRelationship\":true}").accept("application/json"))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/v1/users/1")));
    }
}

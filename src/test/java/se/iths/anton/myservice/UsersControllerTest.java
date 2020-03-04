package se.iths.anton.myservice;

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



import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(UsersController.class)
@Import({UsersModelAssembler.class})
class UsersControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsersRepository repository;
    @BeforeEach
    void setup(){

        when(repository.findAll()).thenReturn(List.of(new User(1, "Anton", "Anton Johansson", "Mölndal", 10000, true),
                new User(0,"Sebbe", "Sebastian Waltilla", "Fjärås", 60000, true),
                new User(0,"Jonte", "Jonathan Holm", "Umeå", 40000, false)));
        when(repository.findById(1)).thenReturn(Optional.of(new User(1, "Anton", "Anton Johansson", "Mölndal", 10000, true)));
        when(repository.save(any(User.class))).thenAnswer(invocationOnMock -> {
            Object[] args = invocationOnMock.getArguments();
            var u = (User) args[0];
            return new User(1, u.getUserName(),u.getRealName(),u.getCity(),u.getIncome(),u.isInRelation());
        });




    }
}

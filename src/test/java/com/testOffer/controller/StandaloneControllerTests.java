package com.testOffer.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testOffer.entity.User;
import com.testOffer.service.Imp.UserServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class StandaloneControllerTests {
	@MockBean
    UserServiceImpl userService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testRegisterUser () throws  Exception{
        User user = new User();
        user.setFirstName("ahmed");
        user.setLastName("Ibn Mtar");
        user.setCity("FRANCE");
        user.setEmail("oussema.mtarr@gmail.com");
        user.setAge(18);
        user.setBirthOfdate(LocalDateTime.of(1993,12,18,0,0));

        Mockito.when(userService.saveUser(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated()) ; 

    }

}

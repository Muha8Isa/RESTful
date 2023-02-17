package se.lexicon.restful.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.restful.model.entity.dto.RoleDto;

@SpringBootTest
@AutoConfigureMockMvc

@Transactional
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc; // Provide required methods to test the controller layer. (POST, GET, PUT..... etc)

     ObjectMapper objectMapper; // if ObjectMapper is defined as a bean inside config class, it can be injected here using @Autowired.

    @BeforeEach
    public void setup() throws Exception { //.perform throws checked exception.
        String requestBodyAdmin = "{ \"name\" : \"ADMIN\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/role/")
                .content(requestBodyAdmin).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("ADMIN"));

        String requestBodyUser = "{ \"name\" : \"USER\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/role/")
                .content(requestBodyUser).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("name").value("USER"));

        objectMapper = new ObjectMapper(); // Jackson ObjectMapper // Converts JSON body to java object. Instantiated manually here because it wasn't defined as a bean.
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formats the JSON output with indentation to make it more human-readable.
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Option is set to false, which means that dates will be serialized in a human-readable format rather than as a numerical timestamp.
        objectMapper.registerModule(new JavaTimeModule()); // Enable support for Java 8 date/time types
    }

    @Test
    public void test_findAll () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/role/"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()) // isOk means 200
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty()) // $ to use array.
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))    // $[0] check the first index of an array.
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("USER"))    // $[0] check the first index of an array.

                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1))    // $[0] check the first index of an array.
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("ADMIN"));     // $[0] check the first index of an array.

        /** The reason for writing that the expected id value is 2 for the first index, and 1 for the second index is because-
        I  have the method List<Role> findAllByOrderByIdDesc(); inside the Rolerepository **/
    }

    @Test
    public void test_createRole () throws Exception {
        String requestBodyGuest = "{ \"name\" : \"GUEST\" }";

       MvcResult mvcResult = mockMvc.
               perform(MockMvcRequestBuilders.post("/api/v1/role/").
                       content(requestBodyGuest)
                       .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

       int actualStatusCode = mvcResult.getResponse().getStatus();
       int expectedStatusCode = 201;

        Assertions.assertEquals(expectedStatusCode, actualStatusCode);

        String responseJSONBody = mvcResult.getResponse().getContentAsString();
        RoleDto actualRoleDto = objectMapper.readValue(responseJSONBody, RoleDto.class); // readValue method is responsible to read the String JSON message and converts it to JAVA object.
        RoleDto expectedRoleDto = new RoleDto(3, "GUEST");
        Assertions.assertEquals(expectedRoleDto.getName(), actualRoleDto.getName());
        Assertions.assertEquals(expectedRoleDto, actualRoleDto); //RoleDto class should have equals and hashcode method to be able to compare these values.
    }
}

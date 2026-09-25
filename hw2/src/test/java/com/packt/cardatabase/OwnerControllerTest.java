package com.packt.cardatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.cardatabase.hw2.repos.CarRepository;
import com.packt.cardatabase.hw2.models.Owner;
import com.packt.cardatabase.hw2.repos.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Owner savedOwner;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        ownerRepository.deleteAll();

        Owner owner = new Owner("John", "Johnson");
        savedOwner = ownerRepository.save(owner);
    }

    @Test
    void testGetOwners() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstname").value("John"))
                .andExpect(jsonPath("$[0].lastname").value("Johnson"));
    }

    @Test
    void testGetOwnerByIdFound() throws Exception {
        mockMvc.perform(get("/owners/" + savedOwner.getOwnerid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Johnson"));
    }

    @Test
    void testGetOwnerByIdNotFound() throws Exception {
        mockMvc.perform(get("/owners/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddOwner() throws Exception {
        Owner newOwner = new Owner("Mary", "Robinson");

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value("Mary"))
                .andExpect(jsonPath("$.ownerid").exists());
    }

    @Test
    void testUpdateOwnerFound() throws Exception {
        Owner updateDetails = new Owner("Johnny", "Johnson");

        mockMvc.perform(put("/owners/" + savedOwner.getOwnerid())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("Johnny"))
                .andExpect(jsonPath("$.lastname").value("Johnson"));
    }

    @Test
    void testUpdateOwnerNotFound() throws Exception {
        Owner updateDetails = new Owner("Johnny", "Johnson");

        mockMvc.perform(put("/owners/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOwnerSuccess() throws Exception {
        mockMvc.perform(delete("/owners/" + savedOwner.getOwnerid()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/owners/" + savedOwner.getOwnerid()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteOwnerNotFound() throws Exception {
        mockMvc.perform(delete("/owners/999999"))
                .andExpect(status().isNotFound());
    }
}

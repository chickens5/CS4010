package com.packt.cardatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packt.cardatabase.hw2.Car;
import com.packt.cardatabase.hw2.CarRepository;
import com.packt.cardatabase.hw2.Owner;
import com.packt.cardatabase.hw2.OwnerRepository;
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
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Owner owner;
    private Car savedCar;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        ownerRepository.deleteAll();

        owner = new Owner("Jane", "Doe");
        owner = ownerRepository.save(owner);

        Car car = new Car("Toyota", "Corolla", "Silver", "ABC-1234", 2021, 20000, owner);
        savedCar = carRepository.save(car);
    }

    @Test
    void testGetCars() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].brand").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Corolla"));
    }

    @Test
    void testGetCarByIdFound() throws Exception {
        mockMvc.perform(get("/cars/" + savedCar.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.registrationNumber").value("ABC-1234"));
    }

    @Test
    void testGetCarByIdNotFound() throws Exception {
        mockMvc.perform(get("/cars/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddCar() throws Exception {
        Car newCar = new Car("Honda", "Civic", "Black", "HND-5678", 2022, 24000, owner);

        mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCar)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.brand").value("Honda"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testUpdateCarFound() throws Exception {
        Car updateDetails = new Car("Toyota", "Camry", "Gold", "ABC-9999", 2023, 28000, owner);

        mockMvc.perform(put("/cars/" + savedCar.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Camry"))
                .andExpect(jsonPath("$.price").value(28000));
    }

    @Test
    void testUpdateCarNotFound() throws Exception {
        Car updateDetails = new Car("Toyota", "Camry", "Gold", "ABC-9999", 2023, 28000, owner);

        mockMvc.perform(put("/cars/999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCarSuccess() throws Exception {
        mockMvc.perform(delete("/cars/" + savedCar.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/cars/" + savedCar.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCarNotFound() throws Exception {
        mockMvc.perform(delete("/cars/999999"))
                .andExpect(status().isNotFound());
    }
}

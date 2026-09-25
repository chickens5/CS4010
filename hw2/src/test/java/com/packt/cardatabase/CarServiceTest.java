package com.packt.cardatabase;

import com.packt.cardatabase.hw2.models.Car;
import com.packt.cardatabase.hw2.repos.CarRepository;
import com.packt.cardatabase.hw2.models.Owner;
import com.packt.cardatabase.hw2.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Owner owner;
    private Car car;

    @BeforeEach
    void setUp() {
        owner = new Owner("John", "Doe");
        car = new Car("Ford", "Mustang", "Red", "XYZ-1234", 2022, 40000, owner);
        car.setId(1L);
    }

    @Test
    void testGetCars() {
        when(carRepository.findAll()).thenReturn(Arrays.asList(car));

        List<Car> cars = carService.getCars();
        assertThat(cars).hasSize(1);
        assertThat(cars.get(0).getBrand()).isEqualTo("Ford");
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testGetCarById() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Optional<Car> found = carService.getCarById(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getModel()).isEqualTo("Mustang");
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testAddCar() {
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car saved = carService.addCar(car);
        assertThat(saved.getBrand()).isEqualTo("Ford");
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testUpdateCarSuccess() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Car updateDetails = new Car("Ford", "Mustang Mach-E", "Blue", "XYZ-9999", 2023, 55000, owner);
        Car updated = carService.updateCar(1L, updateDetails);

        assertThat(updated.getModel()).isEqualTo("Mustang Mach-E");
        assertThat(updated.getColor()).isEqualTo("Blue");
        assertThat(updated.getPrice()).isEqualTo(55000);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testUpdateCarNotFound() {
        when(carRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> carService.updateCar(2L, car))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not exist");
    }

    @Test
    void testDeleteCarSuccess() {
        when(carRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carRepository).deleteById(1L);

        carService.deleteCar(1L);
        verify(carRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCarNotFound() {
        when(carRepository.existsById(2L)).thenReturn(false);

        assertThatThrownBy(() -> carService.deleteCar(2L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("does not exist");
    }
}

package com.packt.cardatabase;

import com.packt.cardatabase.hw2.models.Car;
import com.packt.cardatabase.hw2.repos.CarRepository;
import com.packt.cardatabase.hw2.models.Owner;
import com.packt.cardatabase.hw2.repos.OwnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    private Owner owner;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        ownerRepository.deleteAll();

        owner = new Owner("Alice", "Smith");
        owner = ownerRepository.save(owner);
    }

    @Test
    void testSaveAndFindById() {
        Car car = new Car("Tesla", "Model 3", "Red", "TES-1234", 2023, 45000, owner);
        Car savedCar = carRepository.save(car);

        assertThat(savedCar.getId()).isNotNull();
        Optional<Car> found = carRepository.findById(savedCar.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getBrand()).isEqualTo("Tesla");
        assertThat(found.get().getOwner().getFirstname()).isEqualTo("Alice");
    }

    @Test
    void testFindAll() {
        Car car1 = new Car("Ford", "Focus", "Blue", "FOR-1111", 2021, 22000, owner);
        Car car2 = new Car("Honda", "Civic", "Black", "HON-2222", 2022, 25000, owner);
        carRepository.save(car1);
        carRepository.save(car2);

        List<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(2);
    }

    @Test
    void testFindByBrandAndColor() {
        Car car1 = new Car("BMW", "M3", "Blue", "BMW-3333", 2023, 75000, owner);
        carRepository.save(car1);

        List<Car> byBrand = carRepository.findByBrand("BMW");
        assertThat(byBrand).hasSize(1);
        assertThat(byBrand.get(0).getModel()).isEqualTo("M3");

        List<Car> byColor = carRepository.findByColor("Blue");
        assertThat(byColor).hasSize(1);
    }

    @Test
    void testDeleteCar() {
        Car car = new Car("Audi", "A4", "White", "AUD-4444", 2020, 38000, owner);
        Car savedCar = carRepository.save(car);

        carRepository.deleteById(savedCar.getId());
        Optional<Car> found = carRepository.findById(savedCar.getId());
        assertThat(found).isEmpty();
    }
}

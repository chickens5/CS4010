// Gabriel J 9/25/26 ~ 1200

package com.packt.cardatabase;

import com.packt.cardatabase.hw2.Car;
import com.packt.cardatabase.hw2.CarRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Car Service layer class injected with carRepository for data access/service
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }


    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car car) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("[updateCar] car with id " + id + " does not exist"));

        if (car.getBrand() != null) {
            existingCar.setBrand(car.getBrand());
        }
        if (car.getModel() != null) {
            existingCar.setModel(car.getModel());
        }
        if (car.getColor() != null) {
            existingCar.setColor(car.getColor());
        }
        if (car.getRegistrationNumber() != null) {
            existingCar.setRegistrationNumber(car.getRegistrationNumber());
        }
        if (car.getModelYear() != 0) {
            existingCar.setModelYear(car.getModelYear());
        }
        if (car.getPrice() != 0) {
            existingCar.setPrice(car.getPrice());
        }
        if (car.getOwner() != null) {
            existingCar.setOwner(car.getOwner());
        }

        return carRepository.save(existingCar);
    }

    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new IllegalStateException("[deleteCar] car with id " + id + " does not exist");
        }
        carRepository.deleteById(id);
    }
}

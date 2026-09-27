// Gabriel J 9/27/26 ~ 1730

package com.packt.cardatabase.hw2.services;

import com.packt.cardatabase.hw2.dto.CarRequest;
import com.packt.cardatabase.hw2.models.Car;
import com.packt.cardatabase.hw2.repos.*;
import com.packt.cardatabase.hw2.models.Owner;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// CarService logic for Car entity management
// Injected with CarRepository and OwnerRepository for CRUD operations and owner verification
@Service
public class CarService {

    private final CarRepository carRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CarService(CarRepository carRepository, OwnerRepository ownerRepository) {
        this.carRepository = carRepository;
        this.ownerRepository = ownerRepository;
    }

    // ====== CRUD Service Methods (uses CarRequest record standardization) =======

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    // Create a Car if ownerId exists
    public Car addCar(CarRequest request) {
        Owner owner = null;
        if (request.ownerId() != null) {
            owner = ownerRepository.findById(request.ownerId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner with id " + request.ownerId() + " does not exist"));
        }

        Car car = new Car(
                request.brand(),
                request.model(),
                request.color(),
                request.registrationNumber(),
                request.modelYear(),
                request.price(),
                owner
        );

        return carRepository.save(car);
    }

    // Update a Car if it's id and ownerId exist
    public Car updateCar(Long id, CarRequest request) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Car with id " + id + " does not exist"));

        if (existingCar.getOwner() != null) {
            if (request.ownerId() == null || !request.ownerId().equals(existingCar.getOwner().getOwnerid())) {
                throw new IllegalArgumentException("Only the car's owner can update this car");
            }
        } else if (request.ownerId() != null) {
            Owner owner = ownerRepository.findById(request.ownerId())
                    .orElseThrow(() -> new IllegalArgumentException("Owner with id " + request.ownerId() + " does not exist"));
            existingCar.setOwner(owner);
        }

        if (request.brand() != null) existingCar.setBrand(request.brand());
        if (request.model() != null) existingCar.setModel(request.model());
        if (request.color() != null) existingCar.setColor(request.color());
        if (request.registrationNumber() != null) existingCar.setRegistrationNumber(request.registrationNumber());
        if (request.modelYear() != 0) existingCar.setModelYear(request.modelYear());
        if (request.price() != 0) existingCar.setPrice(request.price());

        return carRepository.save(existingCar);
    }
    // Delete Car if it's id exists
    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new IllegalStateException("Car with id " + id + " does not exist");
        }
        carRepository.deleteById(id);
    }
}
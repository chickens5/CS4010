// Gabriel J 9/22/26 ~ 1630

package com.packt.cardatabase.hw2.controllers;

import com.packt.cardatabase.hw2.dto.CarRequest;
import com.packt.cardatabase.hw2.models.Car;
import com.packt.cardatabase.hw2.services.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// CarController handles HTTP REST requests for the /cars
// Injected with CarService to delegate CRUD operations and handle Car DTO request mapping
@RestController
@RequestMapping(path = {"/cars"})
@CrossOrigin
public class CarController {

	private final CarService carService;

	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping
	public ResponseEntity<List<Car>> getCars() {
		return new ResponseEntity<>(carService.getCars(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		Optional<Car> car = carService.getCarById(id);
		return car.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<?> addCar(@RequestBody CarRequest request) {
		try {
			Car createdCar = carService.addCar(request);
			return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody CarRequest request) {
		try {
			Car updatedCar = carService.updateCar(id, request);
			return new ResponseEntity<>(updatedCar, HttpStatus.OK);
		} catch (IllegalStateException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
		try {
			carService.deleteCar(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (IllegalStateException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

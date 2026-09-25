// Gabriel J 9/22/26 ~ 1630

package com.packt.cardatabase;

import com.packt.cardatabase.hw2.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
		List<Car> cars = carService.getCars();
		return new ResponseEntity<>(cars, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		Optional<Car> car = carService.getCarById(id);
		return car.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<Car> addCar(@RequestBody Car car) {
		Car createdCar = carService.addCar(car);
		return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
		try {
			Car updatedCar = carService.updateCar(id, car);
			return new ResponseEntity<>(updatedCar, HttpStatus.OK);
		} catch (IllegalStateException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

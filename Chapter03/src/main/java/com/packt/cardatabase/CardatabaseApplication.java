//Professor Henry Kang | CS 4010
// Gabriel J ~ Lat updated: August 30, 2026

//our app domain
package com.packt.cardatabase;

//final utility class providing statics methods to manipulate arrays.
import java.util.Arrays;

//SLF4J (Simple Logging Facade for Java)
// is a universal abstraction layer
// and interface for various logging frameworks in Java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//domain classes
import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
	// Assigns logger to our application class
	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
	);

	private final CarRepository repository;
	private final OwnerRepository orepository;

	public CardatabaseApplication(CarRepository repository,
								  OwnerRepository orepository)
	{
		this.repository = repository;
		this.orepository = orepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Application started");
	}

	@Override
	public void run(String... args) throws Exception {
		//Defines compact data arrays for Owners, Cars, & (next) Pets
		String[][] ownerData = {
				{"John", "Johnson"}, {"Mary", "Robinson"}, {"David", "Smith"}, {"Sarah", "Miller"}, {"James", "Davis"},
				{"Emily", "Garcia"}, {"Michael", "Rodriguez"}, {"Jessica", "Martinez"}, {"Robert", "Hernandez"}, {"Linda", "Lopez"}
		};

		Object[][] carData = {
				{"Ford", "Mustang", "Red", "ADF-1121", 2023, 59000},
				{"Nissan", "Leaf", "White", "SSJ-3002", 2020, 29000},
				{"Toyota", "Prius", "Silver", "KKO-0212", 2022, 39000},
				{"Honda", "Civic", "Black", "BBA-4455", 2021, 25000},
				{"Tesla", "Model 3", "Blue", "TSL-7788", 2023, 45000},
				{"Chevrolet", "Bolt", "Grey", "CHV-9900", 2022, 28000},
				{"BMW", "3 Series", "White", "BMW-1122", 2023, 48000},
				{"Audi", "A4", "Black", "AUD-3344", 2021, 42000},
				{"Hyundai", "Elantra", "Blue", "HYU-5566", 2020, 21000},
				{"Kia", "Sportage", "Red", "KIA-7788", 2022, 32000}
		};

		//Processes data sequentially to keep memory overhead minimal
		for (int i = 0; i < ownerData.length; i++) {
			//Instantiates, saves, and frees the owner memory reference inside the loop scope
			Owner owner = orepository.save(new Owner(ownerData[i][0], ownerData[i][1]));

			//Instantiates, saves, and frees the car memory reference inside the loop scope
			repository.save(new Car(
					(String) carData[i][0],  // Brand
					(String) carData[i][1],  // Model
					(String) carData[i][2],  // Color
					(String) carData[i][3],  // Register Number
					(Integer) carData[i][4], // Year
					(Integer) carData[i][5], // Price
					owner                    // Owner entity link
			));
		}

		// Fetches all cars and logs to console
		for (Car car : repository.findAll()) {
			logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
		}
	}
}

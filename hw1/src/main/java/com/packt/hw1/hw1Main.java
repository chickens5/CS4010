//Professor Henry Kang | CS 4010
// Gabriel J ~ Lat updated: Sep 13, 2026

// This file runs the spring boot application, initializes the repositories,
//  inserts data into cardb (create-drop), and logs the data.

// app domain
package com.packt.hw1;
import com.packt.hw1.domain.Car;
import com.packt.hw1.domain.CarRepository;
import com.packt.hw1.domain.Owner;
import com.packt.hw1.domain.OwnerRepository;
import com.packt.hw1.domain.Pet;
import com.packt.hw1.domain.PetRepository;

// Array methods
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

//SLF4J (Simple Logging Facade for Java)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class hw1Main implements CommandLineRunner {
	// Assigns logger to our application class
	private static final Logger logger = LoggerFactory.getLogger(
			hw1Main.class
	);
// Declares Car, Pet, & Owner repositories
	private final CarRepository repository;
	private final OwnerRepository orepository;
	private final PetRepository prepository;

// Constructor injection for the repos
	public hw1Main(CarRepository repository,
				   OwnerRepository orepository,
				   PetRepository prepository)
	{
		this.repository = repository;
		this.orepository = orepository;
		this.prepository = prepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(hw1Main.class, args);
		logger.info("Application started");
	}

	@Override
	public void run(String... args) throws Exception {

		// Initializes Owner objects & saves all as list into Owner repository
		Owner owner1 = new Owner("Katlyn", "MyLove");
		Owner owner2 = new Owner("Jane", "Dan");
		Owner owner3 = new Owner("Gabriel", "Jackson");
		Owner owner4 = new Owner("Henry", "Kang");
		Owner owner5 = new Owner("Joe", "Dirt");
		Owner owner6 = new Owner("Ann", "Perkins");
		Owner owner7 = new Owner("Michael", "Lee");
		Owner owner8 = new Owner("Mace", "Windu");
		Owner owner9 = new Owner("Tony", "Soprano");
		Owner owner10 = new Owner("Truman", "Burbank");

		orepository.saveAll(Arrays.asList(owner1, owner2, owner3, owner4, owner5,
				owner6, owner7, owner8, owner9, owner10));

		// Initializes Car objects and individually saves each into Car repository

		repository.save(new Car("Infiniti", "G35 Coupe", "Black",
				"CA-RIP", 2005, 5000, owner3));

		repository.save(new Car("Chevorlet", "Spark",
				"White", "ADF-8742", 2023, 15000,
				owner2));
		repository.save(new Car("Fiat", "500e",
				"Yellow", "ADF-2302", 2023, 20000,
				owner3));
		repository.save(new Car("Toyota", "Supra",
				"Black", "ADF-9110", 2023, 45000,
				owner1));
		repository.save(new Car("Mittsubishi", "Lancer Evo",
				"Green", "PRO-6060", 1990, 100000,
				owner1));
		repository.save(new Car("Chevorlet", "Camaro",
				"Red", "ADF-0061", 2023, 59000,
				owner8));
		repository.save(new Car("Tesla", "S",
				"Grey", "ADF-1121", 2024, 1,
				owner7));
		repository.save(new Car("Volkswagen", "Golf R",
				"Dark Navy", "FL-0410", 2022, 30000,
				owner8));
		repository.save(new Car("Ford", "Mustang",
				"Black", "ADF-0390", 2023, 59000,
				owner9));
		repository.save(new Car("Ford", "F150",
				"Pink as heck", "ADF-2040", 2025, 50000,
				owner10));

		// Initializes Pet objects & saves all as list into Pet repository

		Pet pet1 = new Pet("Coco", "Oriental", LocalDate.of(2019, 1, 1), owner1);
		Pet pet2 = new Pet("Miso", "British Shorthair", LocalDate.of(2022, 2, 1), owner1);
		Pet pet3 = new Pet("Bagel", "Unknown", LocalDate.of(2022, 3, 1), owner2);
		Pet pet4 = new Pet("Kamaji", "STL Longhair", LocalDate.of(2022, 4, 1), owner3);
		Pet pet5 = new Pet("Ranger", "Bearded Dragon", LocalDate.of(2008, 4, 1), owner3);
		Pet pet6 = new Pet("Spaghettios", "Pitbull", LocalDate.of(2022, 10, 1), owner9);
		Pet pet7 = new Pet("Jesse", "Bengal", LocalDate.of(2015, 2, 1), owner10);
		Pet pet8 = new Pet("Burton Guster", "Tabby", LocalDate.of(2022, 11, 1), owner4);
		Pet pet9 = new Pet("Sir Reginald", "Maine Coon", LocalDate.of(2011, 2, 23), owner6);
		Pet pet10 = new Pet("Old Yeller", "Labrador Retriever", LocalDate.of(1957, 2, 13), owner8);
		prepository.saveAll(List.of(pet1, pet2, pet3, pet4, pet5, pet6, pet7, pet8, pet9, pet10));

		// Fetches all cars, owners, & pets objects in their respective repositories and logs to console
		logger.info("Getting Cars...");
		for (Car car : repository.findAll()) {
			logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
		}
		logger.info("Getting Owners...");
		for (Owner owner : orepository.findAll()) {
			logger.info("firstname: {}, lastname: {}", owner.getFirstname(), owner.getLastname());
		}
		logger.info("Getting Pets...");
		for (Pet pet : prepository.findAll()) {
			logger.info("name: {}, species: {}, dob: {}", pet.getName(),
					pet.getSpecies(), pet.getDob());

		}
	}
}

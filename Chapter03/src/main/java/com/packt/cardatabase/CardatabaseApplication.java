//Professor Henry Kang | CS 4010
// Gabriel J ~ Lat updated: Sep 7, 2026

// app domain
package com.packt.cardatabase;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.Owner;
import com.packt.cardatabase.domain.OwnerRepository;
import com.packt.cardatabase.domain.Pet;
import com.packt.cardatabase.domain.PetRepository;

// Array methods
import java.util.Arrays;

//SLF4J (Simple Logging Facade for Java)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
	// Assigns logger to our application class
	private static final Logger logger = LoggerFactory.getLogger(
			CardatabaseApplication.class
	);

	private final CarRepository repository;
	private final OwnerRepository orepository;
	private final PetRepository prepository;

	public CardatabaseApplication(CarRepository repository,
								  OwnerRepository orepository,
								  PetRepository prepository)
	{
		this.repository = repository;
		this.orepository = orepository;
		this.prepository = prepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Application started");
	}

	@Override
	public void run(String... args) throws Exception {

		Owner owner1 = new Owner("John", "Doe");
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

		logger.info("Repository after adding owners: {}", orepository.findAll());


		repository.save(new Car("Infiniti", "G35 Coupe", "Black",
				"CA-RIP", 2005, 5000, owner1));

		repository.save(new Car("Chevorlet", "Spark",
				"White", "ADF-8742", 2023, 15000,
				owner2));
		repository.save(new Car("Fiat", "500e",
				"Yellow", "ADF-2302", 2023, 20000,
				owner3));
		repository.save(new Car("Toyota", "Supra",
				"Black", "ADF-9110", 2023, 45000,
				owner4));
		repository.save(new Car("Mittsubishi", "Lancer Evo",
				"Green", "PRO-6060", 1990, 100000,
				owner1));
		repository.save(new Car("Chevorlet", "Camaro",
				"Red", "ADF-0061", 2023, 59000,
				owner6));
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

		logger.info("Repository after adding cars: {}", repository.findAll());

		Pet pet1 = new Pet("Coco", "Oriental", "January 1, 2019", owner1);
		prepository.save(pet1);


		// Fetches all cars and logs to console
		for (Car car : repository.findAll()) {
			logger.info("brand: {}, model: {}", car.getBrand(), car.getModel());
		}
		for (Owner owner : orepository.findAll()) {
			logger.info("firstname: {}, lastname: {}", owner.getFirstname(), owner.getLastname());
		}
		for (Pet pet : prepository.findAll()) {
			logger.info("name {}, species: {}, dob: {}", pet.getName(),
					pet.getSpecies(), pet.getDob());

		}
	}
}

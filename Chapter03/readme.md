## Welcome
This is project 1 (Chapter 3)

This Spring boot application initializes & constructs objects of 3 entity classes and populates the data into 
cardb (postgres).

- 3 Entities (`Owner`, `Car`, `Pet`): `Car` and `Pet` have a Many-to-One relationship with `Owner` (One-to-Many).
- 3 Repositories (`OwnerRepository`, `CarRepository`, `PetRepository`) extending `CrudRepository`.
- 1 CommandLineRunner runner component populating database records (10 owners, 10 cars, 10 pets)
   at startup and logging their details via `logger.info`.

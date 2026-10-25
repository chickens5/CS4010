// Gabriel J 10/25/26 ~ 1600 | Modified from school-api

package com.cs4010.school;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cs4010.school.domain.AppUser;
import com.cs4010.school.domain.AppUserRepository;
import com.cs4010.school.domain.Student;
import com.cs4010.school.domain.StudentRepository;

@SpringBootApplication
public class hw3 implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(hw3.class);
	private final StudentRepository repository;
	private final AppUserRepository arepository;

	public hw3(StudentRepository repository, AppUserRepository arepository) {
		this.repository = repository;
		this.arepository = arepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(hw3.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PasswordEncoder encoder = new BCryptPasswordEncoder();

		Student stud1 = new Student(12345, "John Smith", "Computer Science", 1);
		Student stud2 = new Student(32289, "Jane Doe", "Physics", 3);
		Student stud3 = new Student(98237, "Peter Parker", "Biology", 2);

		// Encodes/hashes passwords with BCrypt
		AppUser user1 = new AppUser("admin", encoder.encode("admin"), "ADMIN");
		AppUser user2 = new AppUser("user", encoder.encode("user"), "USER");

		arepository.save(user1);
		arepository.save(user2);
		repository.saveAll(Arrays.asList(stud1, stud2, stud3));
	}
}
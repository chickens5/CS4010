// Gabriel J 11/8/26 ~ 1620
package hw4.server;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

// Runs the app
@SpringBootApplication
public class SchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}

// Injected with the repository
	@Autowired
	StudentRepository studentRepository;

// initializes the table with three entries
	@PostConstruct
	public List<Student> initTable() {
		List<Student> list = studentRepository.saveAll(List.of(
				new Student(12345, "Emily Blunt", "Art", 4),
				new Student(43632, "Rebecca Ferguson", "Physics", 3),
				new Student(23095, "Jennifer Lawrence", "Biology", 2)
				));
		return list;
	}
}

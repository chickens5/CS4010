// Gabriel J 11/4/26 ~ 1600 
package hw4.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Spring Data JPA repo interface
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Integer studentId);
    void deleteByStudentId(Integer studentId);
}

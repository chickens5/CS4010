// Gabriel J 11/4/26 ~ 1600

package hw4.server;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// CRUD Business logic for Student
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    // Injected with the repo
    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentByStudentId(Integer studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    public Student addStudent(Student student) {
        logger.info("\n=======\n" + "Adding student {}... \n=======\n",
                student.getStudentId());
        return studentRepository.save(student);
    }

    public Student updateStudent(Integer studentId, Student student) {
        Student existingStudent = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalStateException
                        ("[updateStudent] student with id " + studentId + " does not exist"));

        existingStudent.setName(student.getName());
        existingStudent.setMajor(student.getMajor());
        existingStudent.setYear(student.getYear());

        logger.info("\n=======\n" + "Updating StudentID {}... \n" + "=======\n",
                studentId);
        return studentRepository.save(existingStudent);
    }

    @Transactional
    public void deleteStudent(Integer studentId) {
        studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalStateException
                        ("[deleteStudent] student with id " + studentId + " does not exist"));

        studentRepository.deleteByStudentId(studentId);
        logger.info("\n======\n" + "StudentID {} deleted successfully... \n=======\n",
                studentId);
    }
}

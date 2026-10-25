// Gabriel J 10/25/26 ~ 1600 | Modified from school-api

package com.cs4010.school.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(Integer studentId);
    void deleteByStudentId(Integer studentId);
}

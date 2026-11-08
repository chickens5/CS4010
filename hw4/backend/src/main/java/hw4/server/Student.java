// Gabriel J 11/4/26 ~ 1600

package hw4.server;

import jakarta.persistence.*;

// Student JPA entity table
@Entity
@Table
public class Student {
    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id; //primary key 

    @Column(nullable = false, unique = true)
    private Integer studentId; // unique student id 

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private Integer year;

    public Student() {
    }

    public Student(Integer studentId, String name, String major, Integer year) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.year = year;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

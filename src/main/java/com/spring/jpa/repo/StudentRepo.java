package com.spring.jpa.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.jpa.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

	Student findStudentByEmail(String email);

}

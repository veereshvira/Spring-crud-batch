package com.spring.jpa.service;

import java.util.List;

import com.spring.jpa.entity.Student;

public interface StudentService {

	public List<Student> addStudents(List<Student> students) ;

	public Student getById(int id);

	public List<Student> getAllStudent();

	public Student deleteStudent(int id) ;

	public Student updateStudent(int id, Student updatedStudent);



}

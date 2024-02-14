package com.spring.jpa.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.jpa.entity.Student;
import com.spring.jpa.exception.ResoucreNotFoundException;
import com.spring.jpa.exception.StudentAlreadyExistException;
import com.spring.jpa.repo.StudentRepo;
import com.spring.jpa.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepo repo;

	@Autowired
	public StudentServiceImpl(StudentRepo repo) {
		this.repo = repo;
	}

	public List<Student> addStudents(List<Student> students) throws StudentAlreadyExistException {
		List<Student> addedStudents = new ArrayList<>();

		for (Student student : students) {
			if (repo.findStudentByEmail(student.getEmail()) != null) {
				throw new StudentAlreadyExistException(
						"Student with email already exists.");
			}
			addedStudents.add(repo.save(student));
		}

		return addedStudents;
	}

	@Override
	public Student getById(int id) throws ResoucreNotFoundException {
		return repo.findById(id).orElseThrow(() -> new ResoucreNotFoundException("Student ID Not Found"));
	}

	@Override
	public List<Student> getAllStudent() {
		return repo.findAll();
	}

	@Override
	public Student deleteStudent(int id) throws ResoucreNotFoundException {
		Optional<Student> studentOptional = repo.findById(id);
		if (studentOptional.isPresent()) {
			Student student = studentOptional.get();
			repo.delete(student);
			return student;
		} else {
			throw new ResoucreNotFoundException("Student with ID not found.");
		}
	}

	@Override
	public Student updateStudent(int id, Student updatedStudent) throws ResoucreNotFoundException {
		Optional<Student> optionalStudent = repo.findById(id);
		if (optionalStudent.isPresent()) {
			Student existingStudent = optionalStudent.get();
			existingStudent.setName(updatedStudent.getName());
			existingStudent.setEmail(updatedStudent.getEmail());
			existingStudent.setNumber(updatedStudent.getNumber());
			return repo.save(existingStudent);
		} else {
			throw new ResoucreNotFoundException("Student not found ");
		}

	}

}

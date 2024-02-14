package com.spring.jpa;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.spring.jpa.entity.Student;
import com.spring.jpa.exception.ResoucreNotFoundException;
import com.spring.jpa.exception.StudentAlreadyExistException;
import com.spring.jpa.repo.StudentRepo;
import com.spring.jpa.serviceImpl.StudentServiceImpl;

@SpringBootTest
public class StudentDatabaseApplicationTests {

    @MockBean
    private StudentRepo repo;

	@Test
	void testAddStudents() {
		Student student = new Student();
		student.setEmail("veeresh@gmail.com");

		List<Student> students = new ArrayList<>();
		students.add(student);

		when(repo.findStudentByEmail(student.getEmail())).thenReturn(null);
		when(repo.save(student)).thenReturn(student);

		StudentServiceImpl studentService = new StudentServiceImpl(repo);

		List<Student> addedStudents = studentService.addStudents(students);

		assertEquals(students, addedStudents);
	}

    @Test
    void testAddStudentsThrowsException() {
        Student student = new Student();
        student.setEmail("veeresh@gmail.com");

        List<Student> students = new ArrayList<>();
        students.add(student);

        when(repo.findStudentByEmail(student.getEmail())).thenReturn(student);

        StudentServiceImpl studentService = new StudentServiceImpl(repo);
        
        assertThrows(StudentAlreadyExistException.class, () -> studentService.addStudents(students));
    }

	@Test
	void testGetById() throws ResoucreNotFoundException {
		Student student = new Student();
		student.setId(1);
		student.setName("veeresh");
		student.setEmail("veeresh@gmail.com");

		when(repo.findById(1)).thenReturn(Optional.of(student));

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		Student retrievedStudent = studentService.getById(1);
		assertEquals(student, retrievedStudent);
	}

	@Test
	void testGetByIdThrowsException() {
		when(repo.findById(1)).thenReturn(Optional.empty());

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		assertThrows(ResoucreNotFoundException.class, () -> studentService.getById(1));
	}

	@Test
	void testGetAllStudent() {
		List<Student> students = new ArrayList<>();
		students.add(new Student());
		students.add(new Student());

		when(repo.findAll()).thenReturn(students);

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		List<Student> retrievedStudents = studentService.getAllStudent();
		assertEquals(students.size(), retrievedStudents.size());
	}

	@Test
	void testDeleteStudent() throws ResoucreNotFoundException {
		Student student = new Student();
		student.setId(1);
		student.setName("Veeresh");
		student.setEmail("veeresh@gmail.com");

		when(repo.findById(1)).thenReturn(Optional.of(student));

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		Student deletedStudent = studentService.deleteStudent(1);
		assertEquals(student, deletedStudent);
		verify(repo).delete(student);
	}

	@Test
	void testDeleteStudentThrowsException() {
		when(repo.findById(1)).thenReturn(Optional.empty());

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		assertThrows(ResoucreNotFoundException.class, () -> studentService.deleteStudent(1));
	}

	@Test
	void testUpdateStudent() throws ResoucreNotFoundException {
		Student existingStudent = new Student();
		existingStudent.setId(1);
		existingStudent.setName("Veeresh");
		existingStudent.setEmail("veeresh@gmail.com");

		Student updatedStudent = new Student();
		updatedStudent.setId(1);
		updatedStudent.setName("Shreyas");
		updatedStudent.setEmail("shreyas@gmail.com");

		when(repo.findById(1)).thenReturn(Optional.of(existingStudent));
		when(repo.save(existingStudent)).thenReturn(updatedStudent);

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		Student result = studentService.updateStudent(1, updatedStudent);
		assertEquals(updatedStudent, result);
	}

	@Test
	void testUpdateStudentThrowsException() {
		when(repo.findById(1)).thenReturn(Optional.empty());

		StudentServiceImpl studentService = new StudentServiceImpl(repo);
		assertThrows(ResoucreNotFoundException.class, () -> studentService.updateStudent(1, new Student()));
	}
}

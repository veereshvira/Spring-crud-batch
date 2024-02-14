package com.spring.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jpa.entity.Student;
import com.spring.jpa.service.StudentService;

@RestController
@Configuration
public class Studentcontroller {

	@Autowired
	private StudentService studentService;

	private JobLauncher jobLauncher;

	private Job job;

	@PostMapping("/add")
	public List<Student> addStudents(@RequestBody List<Student> students) {
		return studentService.addStudents(students);
	}

	@GetMapping("/getById/{id}")
	public Student getStudentById(@PathVariable("id") int id) {
		Student student = studentService.getById(id);
		return student;
	}

	@GetMapping("/getAll")
	public List<Student> getAllStudent() {
		List<Student> student = studentService.getAllStudent();
		return student;
	}

	@DeleteMapping("delete/{id}")
	public String deleteStudent(@PathVariable("id") int id) {
		Student student = studentService.deleteStudent(id);
		return "Student Data Deleted Successfully";

	}

	@PutMapping("/update/{id}")
	public Object updateStudent(@PathVariable("id") int id, @RequestBody Student updatedStudent) {
		Student updated = studentService.updateStudent(id, updatedStudent);
		return updated;
	}

	// batch processing
	@GetMapping("/students")
	public void loadCsvToDb() throws Exception {

		JobParameters jobParameters = new JobParametersBuilder().addLong("Start-At", System.currentTimeMillis())
				.toJobParameters();

		jobLauncher.run(job, jobParameters);
	}

}

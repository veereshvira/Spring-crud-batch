package com.spring.jpa.batch;

import org.springframework.batch.item.ItemProcessor;

import com.spring.jpa.entity.Student;

public class StudentProcessor implements ItemProcessor<Student, Student> {

	@Override
	public Student process(Student item) throws Exception {
		return item;
	}

}

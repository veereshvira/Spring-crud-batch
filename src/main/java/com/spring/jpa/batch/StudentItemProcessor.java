package com.spring.jpa.batch;

import org.springframework.batch.item.ItemProcessor;

import com.spring.jpa.entity.Student;

public class StudentItemProcessor implements ItemProcessor<Student, Student>{

	@Override
	public Student process(Student student) throws Exception {
		// TODO Auto-generated method stub
		return student;
	}

}

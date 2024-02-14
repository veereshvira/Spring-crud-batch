package com.spring.jpa.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.spring.jpa.entity.Student;
import com.spring.jpa.repo.StudentRepo;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private StudentRepo studentRepo;

	private StepBuilder stepBuilder;

	private StepBuilder jobBuilder;

	private JobRepository jobRepository;

	private PlatformTransactionManager transactionManager;

	// create Reader
	@Bean
	public FlatFileItemReader<Student> studentReader() {

		FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();

		itemReader.setResource(new FileSystemResource("src/main/resources/studentdb.csv"));
		itemReader.setName("csv-reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	private LineMapper<Student> lineMapper() {
		DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("student_id", "student_email-id", "student_name", "student_moble_number");

		BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Student.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
	}

	// create Processor
	@Bean
	public StudentProcessor studentProcessor() {
		return new StudentProcessor();
	}

	// create writer
	@Bean
	public RepositoryItemWriter<Student> studentWriter() {
		RepositoryItemWriter<Student> repositoryWriter = new RepositoryItemWriter<>();
		repositoryWriter.setRepository(studentRepo);
		repositoryWriter.setMethodName("save");

		return repositoryWriter;
	}

//	// create step
//	@Bean
//	public Step step() {
//		return stepBuilder.get("step-1").<Student, Student>chunk(10).reader(studentReader())
//				.processor(studentProcessor()).writer(studentWriter()).build();
//	}
//	
//	// create job
//	@Bean
//	public Job job() {
//		return jobBuilder.get("student-job")
//								.flow(step())
//								.end()
//								.build();
//	}

	// create step
	@Bean
	public Step step1() {
		return new StepBuilder("step -1", jobRepository)
				.<Student, Student>chunk(10, transactionManager)
				.reader(studentReader())
				.processor(studentProcessor())
				.writer(studentWriter())
				.build();
	}

	// create job
//	@Bean
//	public Job runJob() {
//		return new JobBuilder("Student", jobRepository).start(step1()).build();
//	}
	
}

package com.joffzhang.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zy
 * @date 2021/1/5 10:54
 */
@SpringBootApplication
public class StudySpringBootApplication {
	private static final String[] ARGS = new String[] { "--foo=bar", "--foo=baz", "--debug", "spring", "boot","a=b" };

	public static void main(String[] args) {
		SpringApplication.run(StudySpringBootApplication.class,ARGS);
		//SpringApplication.run(StudySpringBootApplication.class,args);
	}
}

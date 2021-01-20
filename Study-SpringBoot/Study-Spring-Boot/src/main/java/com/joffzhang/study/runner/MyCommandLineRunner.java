package com.joffzhang.study.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author zy
 * @date 2021/1/12 14:43
 */
@Component
public class MyCommandLineRunner implements CommandLineRunner {
	public static final Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);
	@Override
	public void run(String... args) throws Exception {
		logger.debug("执行【com.joffzhang.study.runner.MyCommandLineRunner#run(String... args)】");
		logger.debug("命令行参数："+args);
		logger.debug(Arrays.asList(args).toString());
	}
}

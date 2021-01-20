package com.joffzhang.study.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author zy
 * @date 2021/1/12 14:42
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
	public static final Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("执行【com.joffzhang.study.runner.MyApplicationRunner#run(ApplicationArguments args)】");
		logger.debug("命令行参数："+args);
		logger.debug("args.getNonOptionArgs()	： "+args.getNonOptionArgs().toString());
		logger.debug("args.getSourceArgs()		： "+ Arrays.asList(args.getSourceArgs()));
	}
}

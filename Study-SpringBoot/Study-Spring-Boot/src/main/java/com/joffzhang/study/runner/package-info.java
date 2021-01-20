package com.joffzhang.study.runner;

/***********************************************************
 * Runner在服务启动之后执行，且执行一次
 * springboot提供两种：
 * 		{@link org.springframework.boot.ApplicationRunner}
 * 		{@link org.springframework.boot.CommandLineRunner}
 *	相同：
 * 		两种都在服务启动完成后执行，并且只执行一次
 * 		两者都能获取到应用的命令行参数
 * 		执行的时机是一致的（可通过Ordered相关接口或注解来实现自定义优先级）
 *	不同：
 *		参数不同：
 *			org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
 *					ApplicationArguments是对传参数组的封装
 *			org.springframework.boot.CommandLineRunner#run(java.lang.String...)
 ***********************************************************/
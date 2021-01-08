package com.joffzhang.httpclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zy
 * @date 2021/1/6 17:31
 */
//标注该类是一个配置类
@Configuration
//该注解的参数对应的类是否存在于类路径中（是否引入相应的jar包），不存在（即没有相关jar）不解析该注解修饰的配置类；
@ConditionalOnClass({HttpClient.class})
//声明开启属性注入  会将会HttpClientProperties 作为一个Bean引入HttpClientAutoConfiguration 中。
@EnableConfigurationProperties(HttpClientProperties.class)
public class HttpClientAutoConfiguration {

	private HttpClientProperties httpClientProperties;

	public HttpClientAutoConfiguration(HttpClientProperties httpClientProperties) {
		this.httpClientProperties = httpClientProperties;
	}
	//相当于原始的在xml文件中配置<bean id="">，声明在方法上用于将实例对象注入Spring上下文中。
	@Bean
	//该注解表示，如果容器中不存在它修饰的类的bean，就自动配置这个bean；可以给该注解传入参数例如@ConditionOnMissingBean(name = “example”)，这个表示如果name为“example”的bean存在，这该注解修饰的代码块不执行。
	@ConditionalOnMissingBean(HttpClient.class)
	public HttpClient httpClient(){
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(httpClientProperties.getConnectTimeout())
				.setSocketTimeout(httpClientProperties.getSocketTimeout()).build();
		HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig)
				.setUserAgent(httpClientProperties.getAgent()).setMaxConnPerRoute(httpClientProperties.getMaxPerRoute())
				.setConnectionReuseStrategy(new NoConnectionReuseStrategy()).build();
		return httpClient;
	}
}

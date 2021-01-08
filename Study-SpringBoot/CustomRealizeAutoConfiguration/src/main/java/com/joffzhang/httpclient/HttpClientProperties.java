package com.joffzhang.httpclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zy
 * @date 2021/1/6 17:29
 */
//@ConfigurationProperties注解作用：读取application.properties文件中的内容，根据配置的prefix属性，将prefix属性
//对应的内容生成键值对复制给使用了@ConfigurationProperties注解类中
//创建好配置信息类之后，使用@ConfigurationProperties注解是会出错 原因是我们没有使用到@EnableConfigurationProperties注解，当我们使用了之后就不会出错了
@ConfigurationProperties(prefix = "spring.httpclient")
public class HttpClientProperties {
	private Integer connectTimeout = 1000;//创建连接的最长时间
	private Integer socketTimeout = 10000;//数据传输的最长时间
	private String agent = "agent";
	private Integer maxPerRoute = 10;//设置每个路由的并发数
	private Integer maxTotal = 50;//最大连接数

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(Integer socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public Integer getMaxPerRoute() {
		return maxPerRoute;
	}

	public void setMaxPerRoute(Integer maxPerRoute) {
		this.maxPerRoute = maxPerRoute;
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}
}

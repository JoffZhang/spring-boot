/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.logging.Log;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.metrics.ApplicationStartup;
import org.springframework.core.metrics.StartupStep;
import org.springframework.util.ReflectionUtils;

/**
 * A collection of {@link SpringApplicationRunListener}.
 *
 * @author Phillip Webb
 */
class SpringApplicationRunListeners {

	private final Log log;

	private final List<SpringApplicationRunListener> listeners;

	private final ApplicationStartup applicationStartup;

	SpringApplicationRunListeners(Log log, Collection<? extends SpringApplicationRunListener> listeners,
			ApplicationStartup applicationStartup) {
		this.log = log;
		this.listeners = new ArrayList<>(listeners);
		this.applicationStartup = applicationStartup;
	}
	// 在run()方法开始执行时，该方法就立即被调用，可用于在初始化最早期时做一些工作
	void starting(ConfigurableBootstrapContext bootstrapContext, Class<?> mainApplicationClass) {
		doWithListeners("spring.boot.application.starting", (listener) -> listener.starting(bootstrapContext),
				(step) -> {
					if (mainApplicationClass != null) {
						step.tag("mainApplicationClass", mainApplicationClass.getName());
					}
				});
	}
	// 当environment构建完成，ApplicationContext创建之前，该方法被调用
	void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
		doWithListeners("spring.boot.application.environment-prepared",
				(listener) -> listener.environmentPrepared(bootstrapContext, environment));
	}
	// 当ApplicationContext构建完成时，该方法被调用
	void contextPrepared(ConfigurableApplicationContext context) {
		doWithListeners("spring.boot.application.context-prepared", (listener) -> listener.contextPrepared(context));
	}
	// 在ApplicationContext完成加载，但没有被刷新前，该方法被调用
	void contextLoaded(ConfigurableApplicationContext context) {
		doWithListeners("spring.boot.application.context-loaded", (listener) -> listener.contextLoaded(context));
	}
	// 在ApplicationContext刷新并启动后，CommandLineRunners和ApplicationRunner未被调用前，该方法被调用
	void started(ConfigurableApplicationContext context) {
		doWithListeners("spring.boot.application.started", (listener) -> listener.started(context));
	}
	// 在run()方法执行完成前该方法被调用
	void running(ConfigurableApplicationContext context) {
		doWithListeners("spring.boot.application.running", (listener) -> listener.running(context));
	}
	// 当应用运行出错时该方法被调用
	void failed(ConfigurableApplicationContext context, Throwable exception) {
		doWithListeners("spring.boot.application.failed",
				(listener) -> callFailedListener(listener, context, exception), (step) -> {
					step.tag("exception", exception.getClass().toString());
					step.tag("message", exception.getMessage());
				});
	}

	private void callFailedListener(SpringApplicationRunListener listener, ConfigurableApplicationContext context,
			Throwable exception) {
		try {
			listener.failed(context, exception);
		}
		catch (Throwable ex) {
			if (exception == null) {
				ReflectionUtils.rethrowRuntimeException(ex);
			}
			if (this.log.isDebugEnabled()) {
				this.log.error("Error handling failed", ex);
			}
			else {
				String message = ex.getMessage();
				message = (message != null) ? message : "no error message";
				this.log.warn("Error handling failed (" + message + ")");
			}
		}
	}

	private void doWithListeners(String stepName, Consumer<SpringApplicationRunListener> listenerAction) {
		doWithListeners(stepName, listenerAction, null);
	}

	private void doWithListeners(String stepName, Consumer<SpringApplicationRunListener> listenerAction,
			Consumer<StartupStep> stepAction) {
		StartupStep step = this.applicationStartup.start(stepName);
		this.listeners.forEach(listenerAction);
		if (stepAction != null) {
			stepAction.accept(step);
		}
		step.end();
	}

}

package io.github.sds0917.docs.spring.web.plugins;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DocumentationPluginsBootstrapper implements SmartLifecycle {

	private Class<?> mainApplicationClass;
	private AtomicBoolean initialized = new AtomicBoolean(false);
	private ApisParse apisParse;

	public DocumentationPluginsBootstrapper(ApisParse apisParse) {
		this.apisParse = apisParse;
	}

	@Override
	public void start() {
		this.mainApplicationClass = deduceMainApplicationClass();
		if (initialized.compareAndSet(false, true)) {
			log.info("Context refreshed");
			apisParse.doParse();
		}
	}

	private Class<?> deduceMainApplicationClass() {
		try {
			StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
			for (StackTraceElement stackTraceElement : stackTrace) {
				log.info(stackTraceElement.getClassName() + "           " + stackTraceElement.getMethodName());
				if ("main".equals(stackTraceElement.getMethodName())) {
					return Class.forName(stackTraceElement.getClassName());
				}
			}
		} catch (ClassNotFoundException ex) {
			// Swallow and continue
		}
		return null;
	}

	@Override
	public void stop() {
		initialized.getAndSet(false);
	}

	@Override
	public boolean isRunning() {
		return initialized.get();
	}

	@Override
	public int getPhase() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isAutoStartup() {
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		callback.run();
	}

}

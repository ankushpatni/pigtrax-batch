package com.pigtrax.batch.core;

import java.util.Map;

public interface Process {
	public void execute(final Map<String, Object> inputMap);
}

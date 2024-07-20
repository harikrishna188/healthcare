package com.healthcare.utility;

import org.slf4j.Logger;

public class HealthCareLoggers {
	
	public static void info(Logger LOGGER, String message) {
		LOGGER.debug("Info :: " + message);
	}

}

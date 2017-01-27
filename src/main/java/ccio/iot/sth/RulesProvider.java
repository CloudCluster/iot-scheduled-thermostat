package ccio.iot.sth;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RulesProvider {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RulesProvider.class);
	
	private static final String RULES_LOCATION = "/opt/ccio-rpi-sth/rules.json";
	private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	public static Rules readRules() {
		return readRules(RULES_LOCATION); //System.getProperty("user.home") + 
	}
	
	public static Rules readRules(String location) {
		Rules model = null;
		try {
			model = MAPPER.readValue(new File(location), Rules.class);
		} catch (IOException e) {
			LOGGER.error("Cannot read rules", e);
		}
		if(model == null){
			model = new Rules();
		}
		return model;
	}
}

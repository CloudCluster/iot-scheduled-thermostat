package ccio.iot.sth;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.temperature.TemperatureScale;

public abstract class RelayController implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(RelayController.class);
	
	private Calendar now;
	private String rulesLocation;
	
	public RelayController() {
		super();
	}
	
	@Override
	public void run() {
		Rules rules = rulesLocation == null?RulesProvider.readRules():RulesProvider.readRules(rulesLocation);
		LOGGER.trace("Got rules: {}", rules);
		
		Rule currentRule = rules.findCurrentRule(now == null?Calendar.getInstance():now);
		LOGGER.debug("Current rule: {}", currentRule);
		
		Double currentTemperature = getTemperature(currentRule.getScale());
		LOGGER.debug("Current temperature: {}", currentTemperature);
		
		if(currentTemperature == null){
			LOGGER.error("Cannot read temperature");
			return;
		}
		
		if((currentTemperature + rules.getTemperatureShift()) < currentRule.getTemperature()){
			LOGGER.debug("Switching relay ON");
			switchRellayOn();
		}else if((currentTemperature - rules.getTemperatureShift()) > currentRule.getTemperature()){
			LOGGER.debug("Switching relay OFF");
			switchRellayOff();
		}	
	}
	
	protected abstract Double getTemperature(TemperatureScale scale);
	protected abstract void switchRellayOn();
	protected abstract void switchRellayOff();
	
	// for testing
	protected void setNow(Calendar now){
		this.now = now;
	}

	public void setRulesLocation(String rulesLocation) {
		this.rulesLocation = rulesLocation;
	}
}

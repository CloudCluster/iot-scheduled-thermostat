package ccio.iot.sth;

import com.pi4j.temperature.TemperatureScale;

public class RellayControllerImpl extends RelaySwitchProcess{

	public String operation;
	
	private Double temperature;
	
	public RellayControllerImpl(Double temperature) {
		super();
		this.temperature = temperature;
	}
	
	@Override
	protected Double getTemperature(TemperatureScale scale) {
		return temperature;
	}
	
	@Override
	protected void switchRellayOn() {
		operation = "ON";
	}

	@Override
	protected void switchRellayOff() {
		operation = "OFF";
	}
}

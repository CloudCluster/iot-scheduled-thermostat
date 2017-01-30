package ccio.iot.sth;

import java.util.Date;

import com.pi4j.temperature.TemperatureScale;

public class TemperatureRecord {

	private Date time;
	private Double temperature;
	private TemperatureScale scale = TemperatureScale.CELSIUS;
	private boolean on;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Double getTemperature() {
		return temperature;
	}
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	public TemperatureScale getScale() {
		return scale;
	}
	public void setScale(TemperatureScale scale) {
		this.scale = scale;
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}

	@Override
	public String toString() {
		return "TemperatureRecord [time=" + time + ", temperature=" + temperature + ", scale=" + scale + ", on=" + on
				+ "]";
	}
}

package ccio.iot.sth;

import com.pi4j.temperature.TemperatureScale;

public class Rule {
	
	public static enum Day{
		SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, WEEKDAY, WEEKEND, HOLYDAY
	}
	
	private Integer startHours;
	private Integer startMinutes;
	private Integer finishHours;
	private Integer finishMinutes;
	private Double temperature = 22.0;
	private TemperatureScale scale = TemperatureScale.CELSIUS;
	private Day day;
	
	public Rule() {
		super();
	}
	
	public Integer getStartHours() {
		return startHours;
	}

	public void setStartHours(Integer startHours) {
		this.startHours = startHours;
	}

	public Integer getStartMinutes() {
		return startMinutes;
	}

	public void setStartMinutes(Integer startMinutes) {
		this.startMinutes = startMinutes;
	}

	public Integer getFinishHours() {
		return finishHours;
	}

	public void setFinishHours(Integer finishHours) {
		this.finishHours = finishHours;
	}

	public Integer getFinishMinutes() {
		return finishMinutes;
	}

	public void setFinishMinutes(Integer finishMinutes) {
		this.finishMinutes = finishMinutes;
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
	
	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	@Override
	public String toString() {
		return "Rule [startHours=" + startHours + ", startMinutes=" + startMinutes + ", finishHours=" + finishHours
				+ ", finishMinutes=" + finishMinutes + ", temperature=" + temperature + ", scale=" + scale + ", day="
				+ day + "]";
	}
}

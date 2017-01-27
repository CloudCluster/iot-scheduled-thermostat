package ccio.iot.sth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Rules {

	public static Rule DEFAULT_RULE = new Rule();
	
	private List<Rule> rules = new ArrayList<>();
	private Double temperatureShift = 0.5;

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public Double getTemperatureShift() {
		return temperatureShift;
	}

	public void setTemperatureShift(Double temperatureShift) {
		this.temperatureShift = temperatureShift;
	}

	public Rule findCurrentRule(Calendar now){
		for(Rule rule : rules){
			Calendar start = null;
			if(rule.getStartHours() != null && rule.getStartMinutes() != null){
				start = Calendar.getInstance();
				start.set(Calendar.HOUR_OF_DAY, rule.getStartHours());
				start.set(Calendar.MINUTE, rule.getStartMinutes());
				start.set(Calendar.SECOND, 0);
				start.set(Calendar.MILLISECOND, 0);
			}
			
			Calendar finish = null;
			if(rule.getFinishHours() != null && rule.getFinishMinutes() != null){
				finish = Calendar.getInstance();
				finish.set(Calendar.HOUR_OF_DAY, rule.getFinishHours());
				finish.set(Calendar.MINUTE, rule.getFinishMinutes());
				finish.set(Calendar.SECOND, 0);
				finish.set(Calendar.MILLISECOND, 0);
			}
			
			if(start != null && finish != null && start.after(finish)){
				Calendar dayEnd = Calendar.getInstance();
				dayEnd.set(Calendar.HOUR_OF_DAY, 23);
				dayEnd.set(Calendar.MINUTE, 59);
				dayEnd.set(Calendar.SECOND, 59);
				dayEnd.set(Calendar.MILLISECOND, 999);
				
				Calendar dayStart = Calendar.getInstance();
				dayStart.set(Calendar.HOUR_OF_DAY, 0);
				dayStart.set(Calendar.MINUTE, 0);
				dayStart.set(Calendar.SECOND, 0);
				dayStart.set(Calendar.MILLISECOND, 0);
				
				if(start.before(now) && dayEnd.after(now) || dayStart.before(now) && finish.after(now)){
					return rule;
				}
			}
			
			if((start == null || start.before(now)) && (finish == null || finish.after(now))){
				return rule;
			}
		}
		
		return DEFAULT_RULE;
	}

	@Override
	public String toString() {
		return "Rules [rules=" + rules + "]";
	}
}

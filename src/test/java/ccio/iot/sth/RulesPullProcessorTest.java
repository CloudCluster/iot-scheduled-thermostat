package ccio.iot.sth;

import java.util.Calendar;

public class RulesPullProcessorTest {

	public static void main(String[] args) {
		System.out.println(15 - Calendar.getInstance().get(Calendar.MINUTE) % 15);
		
		RulesPullProcess process = new RulesPullProcess();
		
		process.run();
	}

}

package ccio.iot.sth;

import java.io.IOException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import ccio.iot.sth.RulesProvider;

public class RulesTest {

	@Test
	public void testFindCurrentRuleNight() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 22);
		nowCal.set(Calendar.MINUTE, 15);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		Assert.assertEquals(18.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
	}
	
	@Test
	public void testFindCurrentRuleEarlyMorning() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 2);
		nowCal.set(Calendar.MINUTE, 45);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		Assert.assertEquals(18.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
	}
	
	@Test
	public void testFindCurrentRuleDay() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 11);
		nowCal.set(Calendar.MINUTE, 30);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		Assert.assertEquals(16.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
	}
	
//	@Test
//	public void testFindCurrentRuleMorning() throws IOException{
//		Calendar nowCal = Calendar.getInstance();
//		nowCal.set(Calendar.HOUR_OF_DAY, 16);
//		nowCal.set(Calendar.MINUTE, 0);
//		nowCal.set(Calendar.SECOND, 0);
//		nowCal.set(Calendar.MILLISECOND, 0);
//		
//		Assert.assertEquals(20.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
//	}
	
	@Test
	public void testFindCurrentWeekday() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 16);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		int day = nowCal.get(Calendar.DAY_OF_WEEK);
		if(day != Calendar.THURSDAY){
			nowCal.add(Calendar.DATE, Calendar.THURSDAY-day);
		}
		
		Assert.assertEquals(23.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
	}
	
	@Test
	public void testFindCurrentWeekend() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 16);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		int day = nowCal.get(Calendar.DAY_OF_WEEK);
		if(day != Calendar.SATURDAY){
			nowCal.add(Calendar.DATE, Calendar.SATURDAY-day);
		}
		
		Assert.assertEquals(25.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
	}
	
	@Test
	public void testFindCurrentMonday() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 16);
		nowCal.set(Calendar.MINUTE, 0);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		int day = nowCal.get(Calendar.DAY_OF_WEEK);
		if(day != Calendar.MONDAY){
			nowCal.add(Calendar.DATE, Calendar.MONDAY-day);
		}
		System.out.println(nowCal.getTime());
		
		Assert.assertEquals(24.0, RulesProvider.readRules("src/test/resources/rules.json").findCurrentRule(nowCal).getTemperature().doubleValue(), 0);
	}
}

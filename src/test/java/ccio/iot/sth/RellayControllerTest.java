package ccio.iot.sth;

import java.io.IOException;
import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

public class RellayControllerTest {

	@Test
	public void testControllerCold() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 22);
		nowCal.set(Calendar.MINUTE, 15);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		RellayControllerImpl controllerTest = new RellayControllerImpl(17.0);
		controllerTest.setNow(nowCal);
		controllerTest.setRulesLocation("src/test/resources/rules.json");
		controllerTest.run();
		
		Assert.assertEquals("ON", controllerTest.operation);
	}
	
	@Test
	public void testControllerWarm() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 22);
		nowCal.set(Calendar.MINUTE, 15);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		RellayControllerImpl controllerTest = new RellayControllerImpl(19.0);
		controllerTest.setNow(nowCal);
		controllerTest.setRulesLocation("src/test/resources/rules.json");
		controllerTest.run();
		
		Assert.assertEquals("OFF", controllerTest.operation);
	}
	
	@Test
	public void testControllerGood() throws IOException{
		Calendar nowCal = Calendar.getInstance();
		nowCal.set(Calendar.HOUR_OF_DAY, 22);
		nowCal.set(Calendar.MINUTE, 15);
		nowCal.set(Calendar.SECOND, 0);
		nowCal.set(Calendar.MILLISECOND, 0);
		
		RellayControllerImpl controllerTest = new RellayControllerImpl(18.7);
		controllerTest.setNow(nowCal);
		controllerTest.setRulesLocation("src/test/resources/rules.json");
		controllerTest.run();
		
		Assert.assertNull(controllerTest.operation);
		
		controllerTest = new RellayControllerImpl(17.3);
		controllerTest.setNow(nowCal);
		controllerTest.setRulesLocation("src/test/resources/rules.json");
		controllerTest.run();
		
		Assert.assertNull(controllerTest.operation);
	}

}

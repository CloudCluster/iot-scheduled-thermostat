package ccio.iot.sth;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ccio.iot.sth.Rules;
import ccio.iot.sth.RulesProvider;

public class RulesProviderTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesProviderTest.class);
	
	@Test
	public void testRules() throws IOException{
		Rules rules = RulesProvider.readRules("src/test/resources/rules.json");
		LOGGER.info("RULES: {}", rules);
		
		Assert.assertNotNull(rules);
		Assert.assertNotNull(rules.getRules());
		Assert.assertEquals(4, rules.getRules().size());
	}
}

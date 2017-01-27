package ccio.iot.sth;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;

public class Main {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private static final GpioController GPIO = GpioFactory.getInstance();
	private static final GpioPinDigitalOutput PIN_RELAY = GPIO.provisionDigitalOutputPin(RaspiPin.GPIO_10, "Relay", PinState.HIGH);
	private static final List<W1Device> W1_DEVICES = new W1Master().getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
	private static final ScheduledExecutorService EXEC_SERVICE = Executors.newSingleThreadScheduledExecutor();
	
    static{
    	PIN_RELAY.setShutdownOptions(true, PinState.LOW);
    }
    
	public static void main(String[] args) {
		LOGGER.info("Starting Scheduled Thermostat");
		
		try{
			EXEC_SERVICE.scheduleWithFixedDelay(new RelayController() {

				@Override
				protected Double getTemperature(TemperatureScale scale) {
					for (W1Device device : W1_DEVICES) {
					    return ((TemperatureSensor) device).getTemperature(scale);
					}
					return null;
				}

				@Override
				protected void switchRellayOn() {
					if(PIN_RELAY.isLow()){
						PIN_RELAY.high();
					}					
				}

				@Override
				protected void switchRellayOff() {
					if(PIN_RELAY.isHigh()){
						PIN_RELAY.low();
					}					
				}
			}, 0, 10, TimeUnit.SECONDS);
			
			Thread.currentThread().join();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			LOGGER.info("Shutting down Scheduled Thermostat");
			GPIO.shutdown();	
			LOGGER.info("Scheduled Thermostat is off");
		}
	}
}
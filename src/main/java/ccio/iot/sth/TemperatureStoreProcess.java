package ccio.iot.sth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pi4j.temperature.TemperatureScale;

public abstract class TemperatureStoreProcess extends S3Process implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(TemperatureStoreProcess.class);
	private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	private static final JavaType TYPE = MAPPER.getTypeFactory().constructCollectionType(List.class, TemperatureRecord.class);
	
	private final List<TemperatureRecord> records = new ArrayList<>();  
	
	@Override
	public void run() {
		LOGGER.debug("Storing Temp to S3");
		if(AwsClients.INSTANCE.getS3Client() == null){
			return;
		}
		
		//Initialize records from S3 file if available
		try {
			if(records.isEmpty()){
				S3Object object = AwsClients.INSTANCE.getS3Client().getObject(
						new GetObjectRequest(AwsClients.BUCKET_NAME, AwsClients.LAST_24_HOURS_FILE_NAME)
					);
				if(object != null){
					String recStr = IOUtils.toString(object.getObjectContent());
					LOGGER.debug(recStr);
					object.close();
					
					if(recStr != null){
						records.addAll(MAPPER.readValue(recStr, TYPE));
					}
				}
			}
		} catch (AmazonS3Exception e){
			s3Exception(e);
		} catch (Throwable e) {
			LOGGER.error("Cannot process S3 object", e);
		}
		
		//Remove old records
		Instant twentyFourHoursEarlier = Instant.now().minus(24, ChronoUnit.HOURS);
		for(Iterator<TemperatureRecord> iter = records.iterator(); iter.hasNext();){
			TemperatureRecord record = iter.next();
			if(record.getTime() == null || record.getTime().toInstant().isBefore(twentyFourHoursEarlier)){
				iter.remove();
			}
		}
		
		TemperatureRecord record = new TemperatureRecord();
		record.setOn(isOn());
		record.setScale(TemperatureScale.CELSIUS);
		record.setTemperature(getTemperature(TemperatureScale.CELSIUS));
		record.setTime(new Date());
		records.add(record);
		
		try {
			AwsClients.INSTANCE.getS3Client().putObject(AwsClients.BUCKET_NAME, AwsClients.LAST_24_HOURS_FILE_NAME, MAPPER.writeValueAsString(records));
		} catch (AmazonS3Exception e){
			s3Exception(e);
		} catch (Throwable e) {
			LOGGER.error("Cannot store S3 object", e);
		}
	}
	
	protected abstract Double getTemperature(TemperatureScale scale);
	protected abstract boolean isOn();

}

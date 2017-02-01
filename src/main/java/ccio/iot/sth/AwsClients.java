package ccio.iot.sth;

import org.slf4j.LoggerFactory;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public enum AwsClients {

	INSTANCE;
	
	public static final String BUCKET_NAME = "iot-scheduled-thermostat";
	public static final String RULES_FILE_NAME = "rules.json";
	public static final String LAST_24_HOURS_FILE_NAME = "last24hours.json";
	
	private AmazonS3 S3Client;
	
	private AwsClients(){
		try{
			S3Client = AmazonS3ClientBuilder.standard()
					.withCredentials(new ProfileCredentialsProvider("/opt/ccio-rpi-sth/aws.credentials", "default"))
					.withRegion(Regions.US_EAST_1)
					.build();
		} catch (Throwable e) {
			LoggerFactory.getLogger(AwsClients.class).error("Cannot initialize S3 Client", e);
		}
	}
	
	public AmazonS3 getS3Client(){
		return S3Client;
	}
}

package ccio.iot.sth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

public class RulesPullProcess implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesPullProcess.class);

	//[default]
	//aws_access_key_id={YOUR_ACCESS_KEY_ID}
	//aws_secret_access_key={YOUR_SECRET_ACCESS_KEY} 
	private static final AmazonS3 S3_CLIENT;
	
	static{
		AmazonS3 client = null;
		try{
			client = AmazonS3ClientBuilder.standard()
					.withCredentials(new ProfileCredentialsProvider("/opt/ccio-rpi-sth/aws.credentials", "default"))
					.withRegion(Regions.US_EAST_1)
					.build();
		} catch (Throwable e) {
			LOGGER.error("Cannot initialize S3 Client", e);
		}
		S3_CLIENT = client;
	}
	
	@Override
	public void run() {
		LOGGER.debug("Pulling rules from S3");
		if(S3_CLIENT == null){
			return;
		}
		try {
			S3Object object = S3_CLIENT.getObject(
					new GetObjectRequest("iot-scheduled-thermostat", "rules.json")
						.withModifiedSinceConstraint(RulesProvider.lastModified())
				);
			if(object != null){
				String rules = IOUtils.toString(object.getObjectContent());
				LOGGER.debug(rules);
				RulesProvider.writeRules(rules);
				object.close();
			}
		} catch (AmazonS3Exception e){
			if(!("NoSuchBucket".equals(e.getErrorCode()) || "NoSuchKey".equals(e.getErrorCode()))){
				LOGGER.error(e.getErrorMessage(), e);
			}
		} catch (Throwable e) {
			LOGGER.error("Cannot process S3 object", e);
		}
	}
}

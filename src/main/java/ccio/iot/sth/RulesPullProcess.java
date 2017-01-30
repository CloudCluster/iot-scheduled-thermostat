package ccio.iot.sth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;

public class RulesPullProcess extends S3Process implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesPullProcess.class);

	@Override
	public void run() {
		LOGGER.debug("Pulling rules from S3");
		if(AwsClients.INSTANCE.getS3Client() == null){
			return;
		}
		try {
			S3Object object = AwsClients.INSTANCE.getS3Client().getObject(
					new GetObjectRequest(AwsClients.BUCKET_NAME, AwsClients.RULES_FILE_NAME)
						.withModifiedSinceConstraint(RulesProvider.lastModified())
				);
			if(object != null){
				String rules = IOUtils.toString(object.getObjectContent());
				LOGGER.debug(rules);
				RulesProvider.writeRules(rules);
				object.close();
			}
		} catch (AmazonS3Exception e){
			s3Exception(e);
		} catch (Throwable e) {
			LOGGER.error("Cannot process S3 object", e);
		}
	}
}

package ccio.iot.sth;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.BucketCrossOriginConfiguration;
import com.amazonaws.services.s3.model.CORSRule;

public class S3Process {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3Process.class);

	protected void s3Exception(AmazonS3Exception e){
		if("NoSuchBucket".equals(e.getErrorCode())){
			try{
				AwsClients.INSTANCE.getS3Client().createBucket(AwsClients.BUCKET_NAME);
				BucketCrossOriginConfiguration configuration = new BucketCrossOriginConfiguration();

				CORSRule rule1 = new CORSRule()
				    .withId("CORSRule1")
				    .withAllowedMethods(Arrays.asList(new CORSRule.AllowedMethods[] { 
				            CORSRule.AllowedMethods.PUT, CORSRule.AllowedMethods.GET, CORSRule.AllowedMethods.HEAD}))
				    .withMaxAgeSeconds(3000)
				    .withAllowedHeaders(Arrays.asList(new String[] {"*"}))
				    .withAllowedOrigins(Arrays.asList(new String[] {"*"}));

				configuration.setRules(Arrays.asList(new CORSRule[] {rule1}));
				AwsClients.INSTANCE.getS3Client().setBucketCrossOriginConfiguration(AwsClients.BUCKET_NAME, configuration);
			}catch(Throwable t){
				LOGGER.error(t.getMessage(), t);
			}
		}else if(!"NoSuchKey".equals(e.getErrorCode())){
			LOGGER.error(e.getErrorMessage(), e);
		}
	}
}

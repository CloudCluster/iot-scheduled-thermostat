package ccio.iot.sth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.s3.model.AmazonS3Exception;

public class S3Process {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(S3Process.class);

	protected void s3Exception(AmazonS3Exception e){
		if("NoSuchBucket".equals(e.getErrorCode())){
			try{
				AwsClients.INSTANCE.getS3Client().createBucket(AwsClients.BUCKET_NAME);
			}catch(Throwable t){
				LOGGER.error(t.getMessage(), t);
			}
		}else if(!"NoSuchKey".equals(e.getErrorCode())){
			LOGGER.error(e.getErrorMessage(), e);
		}
	}
}

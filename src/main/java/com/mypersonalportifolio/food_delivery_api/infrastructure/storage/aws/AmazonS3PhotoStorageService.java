package com.mypersonalportifolio.food_delivery_api.infrastructure.storage.aws;

import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoStorageService;
import com.mypersonalportifolio.food_delivery_api.application.storage.PhotoToStoreDTO;
import com.mypersonalportifolio.food_delivery_api.application.storage.fileDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Primary
@Component("S3StorageService")
public class AmazonS3PhotoStorageService implements PhotoStorageService {

    private final S3Client s3Client;
    private final String BUCKET_NAME;
    private final String BUCKET_DIRECTORY;

    @Autowired
    public AmazonS3PhotoStorageService(S3Client s3Client,
            @Value("${application.storage.aws-s3.bucket}")  String bucketName,
            @Value("${application.storage.aws-s3.bucket-directory}") String bucketDirectory) {
        this.s3Client = s3Client;
        BUCKET_NAME = bucketName;
        BUCKET_DIRECTORY = bucketDirectory.concat("/");
    }

    @Override
    public void storePhoto(PhotoToStoreDTO newPhoto) throws IOException {
       var objectRequest = PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(BUCKET_DIRECTORY.concat(newPhoto.getFileName()))
                        .contentType(newPhoto.getFileContentType())
                        .acl(ObjectCannedACL.PUBLIC_READ)
                        .build();

        s3Client.putObject(objectRequest, RequestBody.fromBytes(newPhoto.getFileInputStream().readAllBytes()));
    }

    @Override
    public void removePhoto(String fileName) {
        var objectRequest = DeleteObjectRequest.builder()
                                .bucket(BUCKET_NAME)
                                .key(BUCKET_DIRECTORY.concat(fileName))
                                .build();

        s3Client.deleteObject(objectRequest);
    }

    @Override
    public fileDetailsDTO retrievePhoto(String fileName) {
        var requestUrl = GetUrlRequest.builder()
                .bucket(BUCKET_NAME).
                key(BUCKET_DIRECTORY.concat(fileName))
                .build();

        var photoUrl = s3Client.utilities().getUrl(requestUrl);
//        var objectRequest = GetObjectRequest.builder()
//                        .bucket(BUCKET_NAME)
//                        .key(BUCKET_DIRECTORY.concat(fileName))
//                        .build();
//
//        var objectAsBytes = s3Client.getObjectAsBytes(objectRequest);

        return new fileDetailsDTO(null, photoUrl);
    }

    @Override
    public boolean exists(String fileName) {
        try {
            var headRequest = HeadObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(BUCKET_DIRECTORY.concat(fileName))
                    .build();
            s3Client.headObject(headRequest);

            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }
}

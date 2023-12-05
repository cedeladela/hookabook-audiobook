package cedeladela.hookabook.service.minio;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String bucketName;

    public MinioService(MinioClient minioClient, @Value("${minio.bucketName}") String bucketName) {
        this.minioClient = minioClient;
        this.bucketName = bucketName;
    }

    public void uploadFile(MultipartFile mpfile, String filename, String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        String objectName = path + filename;
        File file = new File("/tmp/" + filename);
        file.canWrite();
        file.canRead();
        try {
            mpfile.transferTo(file);
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(file.getAbsolutePath())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    public void uploadAudioBookZip(MultipartFile file, String filename) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        uploadFile(file, filename, "audiobooks/");
    }

    public void uploadCoverImage(MultipartFile file, String filename) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
        uploadFile(file, filename, "cover-images/");
    }

    public String getAudioBookZipUrl(String filename) throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object("audiobooks/" + filename)
                        .build()
        );
    }

    public String getCoverImageUrl(String filename) throws MinioException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object("cover-images/" + filename)
                        .build()
        );
    }
}

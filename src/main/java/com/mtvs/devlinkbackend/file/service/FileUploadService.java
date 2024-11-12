package com.mtvs.devlinkbackend.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mtvs.devlinkbackend.file.entity.File;
import com.mtvs.devlinkbackend.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {

    private final AmazonS3Client amazonS3Client;

    private final FileRepository fileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileUploadService(AmazonS3Client amazonS3Client, FileRepository fileRepository) {
        this.amazonS3Client = amazonS3Client;
        this.fileRepository = fileRepository;
    }

    public String uploadPublicReadFile(MultipartFile file, String filePath) {
        String fileName = filePath + file.getOriginalFilename();
        // filePath는 /를 하지 않고 시작해야 함

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 여러 파일을 한 번에 저장하는 메서드 추가
    public List<String> uploadPublicReadFiles(MultipartFile[] files, String filePath) {
        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileUrl = uploadPublicReadFile(file, filePath); // 각 파일을 업로드
            fileUrls.add(fileUrl); // 업로드된 파일의 URL을 리스트에 추가
        }
        for (String url : fileUrls) {
            File fileEntity = new File(filePath, url);
            fileRepository.save(fileEntity);
        }
        return fileUrls;
    }

    public void deleteFile(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }

    public List<String> updatePublicReadFile(List<String> oldFileUrlList, MultipartFile[] newFileList, String filePath) {
        for (String fileUrl : oldFileUrlList) {
            String fileName = fileUrl.substring(fileUrl.indexOf("/") + 1);
            if(amazonS3Client.doesObjectExist(bucket, fileName))
                deleteFile(fileName);
            else
                throw new IllegalArgumentException("잘못된 파일 경로 삭제 에러");
        }
//        return uploadPublicReadFiles(newFileList, filePath);
        return null;
    }
}

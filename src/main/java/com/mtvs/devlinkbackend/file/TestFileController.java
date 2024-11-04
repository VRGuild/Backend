package com.mtvs.devlinkbackend.file;

import com.mtvs.devlinkbackend.file.service.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class TestFileController {
    private final FileUploadService fileUploadService;

    public TestFileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PostMapping
    public ResponseEntity<?> testUpload(@RequestBody MultipartFile[] multipartFiles) {
        List<String> fileUrlList = fileUploadService.uploadPublicReadFiles(multipartFiles, "test/");
        return ResponseEntity.ok(fileUrlList);
    }
}

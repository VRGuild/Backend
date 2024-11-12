package com.mtvs.devlinkbackend.file;

import com.mtvs.devlinkbackend.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> testUpload(
            @Parameter(content = @Content(mediaType = "multipart/form-data"))
            @ModelAttribute MultipartFile[] multipartFiles) {
        List<String> fileUrlList = fileUploadService.uploadPublicReadFiles(multipartFiles, "test/");
        return ResponseEntity.ok(fileUrlList);
    }
}

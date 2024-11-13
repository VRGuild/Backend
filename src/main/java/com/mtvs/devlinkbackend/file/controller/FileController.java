package com.mtvs.devlinkbackend.file.controller;


import com.mtvs.devlinkbackend.file.dto.FileResponseDTO;
import com.mtvs.devlinkbackend.file.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @Operation(summary = "Upload files",
            description = "Upload multiple files and receive their URLs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터")
    })
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<FileResponseDTO> fileUpload(
            @Parameter(description = "Files to upload", required = true)
            @RequestParam("multipartFiles") MultipartFile[] multipartFiles,
            @RequestParam("filePath") String filePath) {
        List<String> fileUrlList = fileUploadService.uploadPublicReadFiles(multipartFiles, filePath);
        FileResponseDTO response = new FileResponseDTO(fileUrlList, "파일 업로드가 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(response);
    }
}


package com.mtvs.devlinkbackend.channel.controller;

import com.amazonaws.Response;
import com.mtvs.devlinkbackend.channel.dto.request.PdfRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.IsSuccessDTO;
import com.mtvs.devlinkbackend.channel.dto.response.MemoResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.response.PdfResponseDTO;
import com.mtvs.devlinkbackend.channel.service.PdfService;
import com.mtvs.devlinkbackend.channel.service.PdfViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tile/pdf")
public class PdfController {
    private final PdfService pdfService;
    private final PdfViewService pdfViewService;

    public PdfController(PdfService pdfService, PdfViewService pdfViewService) {
        this.pdfService = pdfService;
        this.pdfViewService = pdfViewService;
    }

    @Operation(summary = "PDF 조회", description = "채널에 대한 PDF들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PDF가 성공적으로 조회되었습니다.",
                    content = @Content(schema = @Schema(implementation = PdfResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @GetMapping("/{channelId}")
    public ResponseEntity<PdfResponseDTO> getPdfByChannelId(@PathVariable String channelId) {
        PdfResponseDTO pdfResponseDTO = pdfViewService.getPdfUrl(channelId);
        return ResponseEntity.ok(pdfResponseDTO);
    }

    @Operation(summary = "PDF 등록", description = "새로운 PDF를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PDF가 성공적으로 등록되었습니다.",
                    content = @Content(schema = @Schema(implementation = IsSuccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/{chnnelId}")
    public ResponseEntity<IsSuccessDTO> registPdf(
            @PathVariable String chnnelId,
            @RequestBody PdfRegistDTO pdfRegistDTO) {
        IsSuccessDTO isSuccessDTO = pdfService.registPdf(pdfRegistDTO, chnnelId);
        return ResponseEntity.ok(isSuccessDTO);
    }

    @Operation(summary = "PDF 수정", description = "PDF를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PDF가 성공적으로 수정되었습니다.",
                    content = @Content(schema = @Schema(implementation = IsSuccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PatchMapping("/{pdfId}")
    public ResponseEntity<IsSuccessDTO> updatePdf(
            @PathVariable String pdfId,
            @RequestBody PdfRegistDTO pdfRegistDTO
    ) {
        IsSuccessDTO isSuccessDTO = pdfService.modifyPdf(pdfRegistDTO, pdfId);
        return ResponseEntity.ok(isSuccessDTO);
    }

    @Operation(summary = "PDF 삭제", description = "PDF를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PDF가 성공적으로 삭제되었습니다.",
                    content = @Content(schema = @Schema(implementation = IsSuccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @DeleteMapping("/{pdfId}")
    public ResponseEntity<IsSuccessDTO> deletePdfByPdfId(@PathVariable String pdfId) {
        IsSuccessDTO isSuccessDTO = pdfService.deletePdfById(pdfId);
        return ResponseEntity.ok().body(isSuccessDTO);
    }
}

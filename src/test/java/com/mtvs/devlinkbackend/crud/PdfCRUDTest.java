package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.channel.dto.request.PdfModifyDTO;
import com.mtvs.devlinkbackend.channel.dto.request.PdfRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.IsSuccessDTO;
import com.mtvs.devlinkbackend.channel.dto.response.PdfResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.PdfInfo;
import com.mtvs.devlinkbackend.channel.repository.PdfRepository;
import com.mtvs.devlinkbackend.channel.repository.PdfViewRepository;
import com.mtvs.devlinkbackend.channel.service.PdfService;
import com.mtvs.devlinkbackend.channel.service.PdfViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PdfCRUDTest {
    @Autowired
    private PdfService pdfService;

    @Autowired
    private PdfViewService pdfViewService;

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private PdfViewRepository pdfViewRepository;

    @Test
    void TestRegistPdf() {
        // 1. PDF 등록을 위한 DTO 생성
        PdfRegistDTO dto = new PdfRegistDTO("http://example.com/sample.pdf");

        // 2. PDF 등록 서비스 호출
        IsSuccessDTO isSuccessDTO = pdfService.registPdf(dto, "channel 1");

        // 3. 등록 성공 여부 확인
        assertThat(isSuccessDTO.isSuccess()).isTrue(); // PDF가 성공적으로 등록되었는지 검증
    }

    @Test
    void TestReadPdf() {
        // 1. 사용할 channelId 설정
        String channelId = "channel 1";

        // 2. 여러 개의 PDF 데이터 생성 및 등록
        for (int i = 0; i < 5; i++) {
            PdfRegistDTO registDTO = new PdfRegistDTO("http://example.com/sample" + (i + 1) + ".pdf");
            IsSuccessDTO isSuccessRegist = pdfService.registPdf(registDTO, channelId);
            assertThat(isSuccessRegist.isSuccess()).isTrue(); // 등록 성공 여부 확인
        }

        // 3. PDF 조회 서비스 호출
        PdfResponseDTO pdfResponseDTO = pdfViewService.getPdfUrl(channelId);

        // 4. 조회된 PDF 확인
        List<PdfInfo> pdfInfoList = pdfResponseDTO.getPdfUrlList();
        assertThat(pdfInfoList).isNotEmpty(); // 조회된 PDF 리스트가 비어있지 않아야 함
        assertThat(pdfInfoList.size()).isEqualTo(5); // 등록한 PDF의 수와 일치해야 함

        // 5. 각 PDF의 URL 확인
        for (int i = 0; i < pdfInfoList.size(); i++) {
            assertThat(pdfInfoList.get(i).getPdfUrl()).isEqualTo("http://example.com/sample" + (i + 1) + ".pdf"); // URL 확인
        }
    }

    @Test
    void TestModifyPdf() {
        // 1. 원본 PDF 등록
        PdfRegistDTO registDTO = new PdfRegistDTO("http://example.com/original.pdf");
        IsSuccessDTO isSuccessRegist = pdfService.registPdf(registDTO, "channel 1");
        assertThat(isSuccessRegist.isSuccess()).isTrue(); // 등록 성공 여부 확인

        // 2. 등록된 PDF 조회
        PdfInfo savedPdf = pdfRepository.findAll().get(0); // 가장 최근에 등록된 PDF 조회

        // 3. 수정할 데이터 준비
        PdfModifyDTO modifyDTO = new PdfModifyDTO(savedPdf.getPdfId(), "http://example.com/modified.pdf");

        // 4. PDF 수정
        IsSuccessDTO isSuccessModify = pdfService.modifyPdf(modifyDTO);
        assertThat(isSuccessModify.isSuccess()).isTrue(); // 수정 성공 여부 확인

        // 5. 수정된 PDF 확인
        Optional<PdfInfo> optionalUpdatedPdf = pdfRepository.findById(savedPdf.getPdfId());
        assertThat(optionalUpdatedPdf).isPresent(); // PDF가 존재해야 함
        assertThat(optionalUpdatedPdf.get().getPdfUrl()).isEqualTo("http://example.com/modified.pdf"); // 수정된 URL 확인
    }

    @Test
    void TestDeletePdf() {
        // 1. 사용할 channelId 설정
        String channelId = "channel 1";

        // 2. PDF 등록을 위한 DTO 생성
        PdfRegistDTO dto = new PdfRegistDTO("http://example.com/delete.pdf");

        // 3. PDF 등록 서비스 호출
        IsSuccessDTO isSuccessRegist = pdfService.registPdf(dto, channelId);
        assertThat(isSuccessRegist.isSuccess()).isTrue(); // PDF가 성공적으로 등록되었는지 확인

        // 4. 등록된 PDF 조회
        List<PdfInfo> pdfInfoList = pdfRepository.findAll();
        assertThat(pdfInfoList).isNotEmpty(); // 조회된 PDF 리스트가 비어있지 않아야 함
        assertThat(pdfInfoList.size()).isEqualTo(1); // 등록한 PDF 수 확인

        // 5. PDF 삭제 서비스 호출
        String pdfIdToDelete = pdfInfoList.get(0).getPdfId(); // 삭제할 PDF의 ID 가져오기
        IsSuccessDTO isSuccessDelete = pdfService.deletePdfById(pdfIdToDelete); // PDF 삭제 호출
        assertThat(isSuccessDelete.isSuccess()).isTrue(); // 삭제 성공 여부 확인

        // 6. 삭제 후 PDF 조회하여 확인
        List<PdfInfo> afterDeletePdfInfoList = pdfRepository.findAll();
        assertThat(afterDeletePdfInfoList).isEmpty(); // 삭제 후 PDF 리스트는 비어있어야 함
    }
}

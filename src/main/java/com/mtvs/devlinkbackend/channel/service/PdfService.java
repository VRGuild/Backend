package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.request.PdfModifyDTO;
import com.mtvs.devlinkbackend.channel.dto.request.PdfRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.IsSuccessDTO;
import com.mtvs.devlinkbackend.channel.entity.PdfInfo;
import com.mtvs.devlinkbackend.channel.repository.PdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PdfService {
    @Autowired
    private PdfRepository pdfRepository;

    @Transactional
    public IsSuccessDTO registPdf(PdfRegistDTO pdfRegistDTO, String channelId) {
        IsSuccessDTO isSuccessDTO = new IsSuccessDTO();
        try {
            PdfInfo pdfInfo = new PdfInfo();
            pdfInfo.setPdfUrl(pdfRegistDTO.getPdfUrl());
            pdfInfo.setChannelId(channelId);
            pdfRepository.save(pdfInfo);
            isSuccessDTO.setSuccess(true);
            isSuccessDTO.setMessage("pdf 등록 성공");
        }
        catch (Exception e) {
            isSuccessDTO.setMessage("pdf 등록 성공");
            isSuccessDTO.setSuccess(false);
            e.printStackTrace();
        }
        return isSuccessDTO;
    }

    @Transactional
    public IsSuccessDTO modifyPdf(PdfRegistDTO pdfRegistDTO, String pdfId) {
        IsSuccessDTO isSuccessDTO = new IsSuccessDTO();
        try {
            Optional<PdfInfo> pdfInfoOptional = pdfRepository.findById(pdfId);
            if (pdfInfoOptional.isPresent()) {
                PdfInfo pdfInfo = pdfInfoOptional.get();
                pdfInfo.setPdfUrl(pdfRegistDTO.getPdfUrl());
                pdfRepository.save(pdfInfo);
                isSuccessDTO.setSuccess(true);
                isSuccessDTO.setMessage("pdf 수정 성공");
            }
        } catch (Exception e) {
            isSuccessDTO.setMessage("pdf 수정 실패");
            isSuccessDTO.setSuccess(false);
            e.printStackTrace();
        }
        return isSuccessDTO;
    }

    @Transactional
    public IsSuccessDTO deletePdfById(String pdfId) {
        IsSuccessDTO isSuccessDTO = new IsSuccessDTO();
        try {
            pdfRepository.deleteById(pdfId);
            isSuccessDTO.setSuccess(true);
            isSuccessDTO.setMessage("pdf 삭제 성공");
            return isSuccessDTO;
        }
        catch (Exception e) {
            isSuccessDTO.setSuccess(false);
            isSuccessDTO.setMessage("pdf 삭제 실패");
            e.printStackTrace();
            return isSuccessDTO;
        }
    }
}

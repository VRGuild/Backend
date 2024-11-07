package com.mtvs.devlinkbackend.experience.command.controller;

import com.mtvs.devlinkbackend.experience.command.model.dto.request.ExperienceRegistRequestDTO;
import com.mtvs.devlinkbackend.experience.command.model.dto.request.ExperienceUpdateRequestDTO;
import com.mtvs.devlinkbackend.experience.command.service.ExperienceService;
import com.mtvs.devlinkbackend.experience.query.model.dto.response.ExperienceSingleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experience")
public class ExperienceCommandController {

    private final ExperienceService experienceService;

    public ExperienceCommandController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @Operation(summary = "새 활동 이력 이력 등록", description = "새 활동 이력 이력를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 등록됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PostMapping
    public ResponseEntity<ExperienceSingleResponseDTO> registExperience(
            @RequestBody ExperienceRegistRequestDTO experienceRegistRequestDTO) {

        ExperienceSingleResponseDTO newExperience = experienceService.registExperience(experienceRegistRequestDTO);
        return ResponseEntity.ok(newExperience);
    }

    @Operation(summary = "Experience 수정", description = "제공된 데이터를 기반으로 특정 Experience를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 수정 데이터"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음")
    })
    @PatchMapping
    public ResponseEntity<ExperienceSingleResponseDTO> updateEther(
            @RequestBody ExperienceUpdateRequestDTO experienceUpdateRequestDTO) {
        try {
            ExperienceSingleResponseDTO updatedExperience = experienceService.updateExperience(experienceUpdateRequestDTO);
            return ResponseEntity.ok(updatedExperience);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Experience 삭제", description = "Experience ID를 사용하여 Experience를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "성공적으로 삭제됨"),
            @ApiResponse(responseCode = "401", description = "인증되지 않음"),
            @ApiResponse(responseCode = "404", description = "Ether를 찾을 수 없음")
    })
    @DeleteMapping("/{expId}")
    public ResponseEntity<Void> deleteEtherByEtherId(@PathVariable Long expId) {
        experienceService.deleteExperienceByExpId(expId);
        return ResponseEntity.noContent().build();
    }
}

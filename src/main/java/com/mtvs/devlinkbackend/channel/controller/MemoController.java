package com.mtvs.devlinkbackend.channel.controller;

import com.mtvs.devlinkbackend.channel.dto.request.MemoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.IsSuccessDTO;
import com.mtvs.devlinkbackend.channel.dto.response.MemoResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.service.MemoService;
import com.mtvs.devlinkbackend.channel.service.MemoViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tile/memo")
public class MemoController {
    private final MemoService memoService;
    private final MemoViewService memoViewService;

    public MemoController(MemoService memoService, MemoViewService memoViewService) {
        this.memoService = memoService;
        this.memoViewService = memoViewService;
    }

    @Operation(summary = "Memo 조회", description = "채널에 대한 메모들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Memo가 성공적으로 조회되었습니다.",
                    content = @Content(schema = @Schema(implementation = MemoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @GetMapping("/{channelId}")
    public ResponseEntity<MemoResponseDTO> getMemoByChannelId(@PathVariable String channelId) {
        MemoResponseDTO memoResponseDTO = memoViewService.getMemo(channelId);
        return ResponseEntity.ok().body(memoResponseDTO);
    }

    @Operation(summary = "Memo 등록", description = "새로운 메모를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Memo가 성공적으로 생성되었습니다.",
                    content = @Content(schema = @Schema(implementation = IsSuccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/{channelId}")
    public ResponseEntity<IsSuccessDTO> registMemoByChannelId(
            @PathVariable String channelId,
            @RequestBody MemoRegistDTO memoRegistDTO) {
        IsSuccessDTO isSuccessDTO = memoService.registMemo(memoRegistDTO, channelId);
        return ResponseEntity.ok().body(isSuccessDTO);
    }

    @Operation(summary = "Memo 수정", description = "Memo를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Memo가 성공적으로 수정되었습니다.",
                    content = @Content(schema = @Schema(implementation = IsSuccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PatchMapping("/{memoId}")
    public ResponseEntity<IsSuccessDTO> updateMemoByMemoId(
            @PathVariable String memoId,
            @RequestBody MemoRegistDTO memoRegistDTO) {
        IsSuccessDTO isSuccessDTO = memoService.modifyMemo(memoRegistDTO, memoId);
        return ResponseEntity.ok().body(isSuccessDTO);
    }

    @Operation(summary = "Memo 삭제", description = "메모를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Memo가 성공적으로 삭제되었습니다.",
                    content = @Content(schema = @Schema(implementation = IsSuccessDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @DeleteMapping("/{memoId}")
    public ResponseEntity<IsSuccessDTO> deleteMemoByMemoId(@PathVariable String memoId) {
        IsSuccessDTO isSuccessDTO = memoService.deleteMemoById(memoId);
        return ResponseEntity.ok().body(isSuccessDTO);
    }
}

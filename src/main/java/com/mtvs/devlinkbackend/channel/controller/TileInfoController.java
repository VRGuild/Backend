package com.mtvs.devlinkbackend.channel.controller;

import com.mtvs.devlinkbackend.channel.dto.request.TileInfoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.TileInfoListResponseDTO;
import com.mtvs.devlinkbackend.channel.service.TileInfoService;
import com.mtvs.devlinkbackend.channel.service.TileInfoViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tile/zone")
public class TileInfoController {

    private final TileInfoService tileInfoService;
    private final TileInfoViewService tileInfoViewService;

    public TileInfoController(TileInfoService tileInfoService, TileInfoViewService tileInfoViewService) {
        this.tileInfoService = tileInfoService;
        this.tileInfoViewService = tileInfoViewService;
    }

    @Operation(summary = "채널 ID로 TileInfo 리스트 조회", description = "특정 채널 ID와 연관된 모든 TileInfo 엔티티를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TileInfo 리스트를 성공적으로 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = TileInfoListResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{channelId}")
    public ResponseEntity<TileInfoListResponseDTO> getTileInfoListByChannelId(@PathVariable String channelId) {
        TileInfoListResponseDTO responseDTO = tileInfoViewService.findTileInfoListByChannelId(channelId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "TileInfo 생성", description = "특정 채널에 TileInfo를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TileInfo가 성공적으로 생성되었습니다.",
                    content = @Content(schema = @Schema(implementation = TileInfoRegistDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/{channelId}")
    public ResponseEntity<TileInfoRegistDTO> insertTileInfo(
            @PathVariable String channelId,
            @RequestBody TileInfoRegistDTO tileInfoRegistDTO) {
        TileInfoRegistDTO responseDTO = tileInfoService.insertTileInfoByChannelId(tileInfoRegistDTO, channelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "TileInfo 업데이트", description = "특정 채널에 존재하는 TileInfo를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TileInfo가 성공적으로 업데이트되었습니다.",
                    content = @Content(schema = @Schema(implementation = TileInfoRegistDTO.class))),
            @ApiResponse(responseCode = "404", description = "TileInfo 또는 채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @PutMapping("/{channelId}/{tileId}")
    public ResponseEntity<TileInfoRegistDTO> updateTileInfo(
            @PathVariable String channelId,
            @PathVariable String tileId,
            @RequestBody TileInfoRegistDTO tileInfoRegistDTO) {
        TileInfoRegistDTO responseDTO = tileInfoService.updateTileInfoByChannelId(tileInfoRegistDTO, channelId, tileId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "TileInfo 삭제", description = "특정 채널에 연관된 모든 TileInfo를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "TileInfo가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/{channelId}/{tileId}")
    public ResponseEntity<Void> deleteTileInfoByChannelId(@PathVariable String channelId, @PathVariable String tileId) {
        tileInfoService.deleteTileInfoByTileId(channelId, tileId);
        return ResponseEntity.noContent().build();
    }
}

package com.mtvs.devlinkbackend.channel.controller;

import com.mtvs.devlinkbackend.channel.dto.request.ObjectInfoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoListResponseDTO;
import com.mtvs.devlinkbackend.channel.dto.response.ObjectInfoSingleResponseDTO;
import com.mtvs.devlinkbackend.channel.service.ObjectInfoService;
import com.mtvs.devlinkbackend.channel.service.ObjectInfoViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tile/object")
public class ObjectInfoController {

    private final ObjectInfoService objectInfoService;
    private final ObjectInfoViewService objectInfoViewService;

    public ObjectInfoController(ObjectInfoService objectInfoService, ObjectInfoViewService objectInfoViewService) {
        this.objectInfoService = objectInfoService;
        this.objectInfoViewService = objectInfoViewService;
    }

    @Operation(summary = "채널 ID로 ObjectInfo 리스트 조회", description = "특정 채널 ID와 연관된 모든 ObjectInfo 엔티티를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ObjectInfo 리스트를 성공적으로 조회했습니다.",
                    content = @Content(schema = @Schema(implementation = ObjectInfoListResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "채널 ID를 찾을 수 없습니다.", content = @Content)
    })
    @GetMapping("/{channelId}")
    public ResponseEntity<ObjectInfoListResponseDTO> getObjectInfoListByChannelId(@PathVariable String channelId) {
        ObjectInfoListResponseDTO responseDTO = objectInfoViewService.findObjectInfoListByChannelId(channelId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "ObjectInfo 생성", description = "새로운 ObjectInfo를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "ObjectInfo가 성공적으로 생성되었습니다.",
                    content = @Content(schema = @Schema(implementation = ObjectInfoSingleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 유효하지 않습니다.", content = @Content)
    })
    @PostMapping("/{channelId}")
    public ResponseEntity<ObjectInfoSingleResponseDTO> insertObjectInfo(
            @PathVariable String channelId,
            @RequestBody ObjectInfoRegistDTO objectInfoRegistDTO) {
        ObjectInfoSingleResponseDTO responseDTO = objectInfoService.insertObjectInfoByChannelId(objectInfoRegistDTO, channelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @Operation(summary = "ObjectInfo 업데이트", description = "기존 ObjectInfo를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ObjectInfo가 성공적으로 업데이트되었습니다.",
                    content = @Content(schema = @Schema(implementation = ObjectInfoSingleResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "ObjectInfo ID를 찾을 수 없습니다.", content = @Content)
    })
    @PutMapping("/{channelId}/{objectId}")
    public ResponseEntity<ObjectInfoSingleResponseDTO> updateObjectInfo(
            @PathVariable String objectId,
            @PathVariable String channelId,
            @RequestBody ObjectInfoRegistDTO objectInfoRegistDTO) {
        ObjectInfoSingleResponseDTO responseDTO = objectInfoService.updateObjectInfoByObjectId(objectInfoRegistDTO, objectId, channelId);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "ObjectInfo 삭제", description = "ObjectInfo를 ID로 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ObjectInfo가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "ObjectInfo ID를 찾을 수 없습니다.", content = @Content)
    })
    @DeleteMapping("/{objectId}")
    public ResponseEntity<Void> deleteObjectInfo(@PathVariable String objectId) {
        objectInfoService.deleteObjectInfoByObjectId(objectId);
        return ResponseEntity.noContent().build();
    }
}

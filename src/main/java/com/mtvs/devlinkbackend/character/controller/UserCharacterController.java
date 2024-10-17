package com.mtvs.devlinkbackend.character.controller;

import com.mtvs.devlinkbackend.character.dto.UserCharacterRegistRequestDTO;
import com.mtvs.devlinkbackend.character.dto.UserCharacterUpdateRequestDTO;
import com.mtvs.devlinkbackend.character.entity.UserCharacter;
import com.mtvs.devlinkbackend.character.service.UserCharacterService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/character")
public class UserCharacterController {
    private final UserCharacterService userCharacterService;
    private final JwtUtil jwtUtil;

    public UserCharacterController(UserCharacterService userCharacterService, JwtUtil jwtUtil) {
        this.userCharacterService = userCharacterService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "캐릭터 등록", description = "새로운 캐릭터를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "캐릭터가 성공적으로 등록되었습니다.")
    @PostMapping
    public ResponseEntity<UserCharacter> registerCharacter(
            @RequestBody UserCharacterRegistRequestDTO userCharacterRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserCharacter userCharacter = userCharacterService.registCharacter(userCharacterRegistRequestDTO, accountId);
        return new ResponseEntity<>(userCharacter, HttpStatus.CREATED);
    }

    @Operation(summary = "캐릭터 조회", description = "계정 ID로 캐릭터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "캐릭터가 성공적으로 조회되었습니다.")
    @ApiResponse(responseCode = "404", description = "해당 계정 ID로 캐릭터를 찾을 수 없습니다.")
    @GetMapping
    public ResponseEntity<UserCharacter> getCharacter(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserCharacter userCharacter = userCharacterService.findCharacterByAccountId(accountId);
        if (userCharacter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userCharacter, HttpStatus.OK);
    }

    @Operation(summary = "캐릭터 수정", description = "계정 ID로 캐릭터를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "캐릭터가 성공적으로 수정되었습니다.")
    @ApiResponse(responseCode = "404", description = "해당 계정 ID로 캐릭터를 찾을 수 없습니다.")
    @PatchMapping
    public ResponseEntity<UserCharacter> updateCharacter(
            @RequestBody UserCharacterUpdateRequestDTO userCharacterUpdateRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        try {
            UserCharacter updatedCharacter = userCharacterService.updateCharacter(userCharacterUpdateRequestDTO, accountId);
            return new ResponseEntity<>(updatedCharacter, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "캐릭터 삭제", description = "계정 ID로 캐릭터를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "캐릭터가 성공적으로 삭제되었습니다.")
    @ApiResponse(responseCode = "404", description = "해당 계정 ID로 캐릭터를 찾을 수 없습니다.")
    @DeleteMapping
    public ResponseEntity<Void> deleteCharacter(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        userCharacterService.deleteCharacterByAccountId(accountId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

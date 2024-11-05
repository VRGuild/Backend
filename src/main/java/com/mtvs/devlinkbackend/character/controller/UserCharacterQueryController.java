package com.mtvs.devlinkbackend.character.controller;

import com.mtvs.devlinkbackend.character.dto.response.UserCharacterSingleResponseDTO;
import com.mtvs.devlinkbackend.character.service.UserCharacterViewService;
import com.mtvs.devlinkbackend.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/character")
public class UserCharacterQueryController {
    private final JwtUtil jwtUtil;
    private final UserCharacterViewService userCharacterViewService;

    public UserCharacterQueryController(JwtUtil jwtUtil, UserCharacterViewService userCharacterViewService) {
        this.jwtUtil = jwtUtil;
        this.userCharacterViewService = userCharacterViewService;
    }

    @Operation(summary = "캐릭터 조회", description = "캐릭터 ID로 캐릭터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "캐릭터가 성공적으로 조회되었습니다.")
    @ApiResponse(responseCode = "404", description = "해당 계정 ID로 캐릭터를 찾을 수 없습니다.")
    @GetMapping
    public ResponseEntity<UserCharacterSingleResponseDTO> getCharacterByCharacterId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        UserCharacterSingleResponseDTO userCharacter = userCharacterViewService.findCharacterByAccountId(accountId);
        if (userCharacter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userCharacter, HttpStatus.OK);
    }

    @Operation(summary = "측정 캐릭터 userId로 조회", description = "유저 ID로 캐릭터를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "캐릭터가 성공적으로 조회되었습니다.")
    @ApiResponse(responseCode = "404", description = "해당 계정 ID로 캐릭터를 찾을 수 없습니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserCharacterSingleResponseDTO> getCharacterByUserId(
            @PathVariable(name = "userId") Long userId) throws Exception {

        UserCharacterSingleResponseDTO userCharacter = userCharacterViewService.findCharacterByUserId(userId);
        if (userCharacter == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userCharacter, HttpStatus.OK);
    }
}

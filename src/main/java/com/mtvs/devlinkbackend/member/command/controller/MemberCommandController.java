package com.mtvs.devlinkbackend.member.command.controller;

import com.mtvs.devlinkbackend.common.util.JwtUtil;
import com.mtvs.devlinkbackend.member.command.service.MemberService;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class MemberCommandController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    public MemberCommandController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "멤버 지원 수락", description = "멤버 지원을 수락합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀이 성공적으로 반영되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping("/accept/{memberId}")
    public ResponseEntity<MemberStatusResponseDTO> updateTeam(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable(name = "memberId") Long memberId) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        MemberStatusResponseDTO memberStatusResponseDTO = memberService.acceptSupplyByMemberId(memberId, accountId);
        return memberStatusResponseDTO != null ?
                ResponseEntity.ok(memberStatusResponseDTO) :
                ResponseEntity.notFound().build();
    }

    @Operation(summary = "멤버 지원 거절", description = "멤버 지원을 거절합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공적으로 반영되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping("/reject/{memberId}")
    public ResponseEntity<MemberStatusResponseDTO> addMemberToTeam(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable(name = "memberId") Long memberId) throws Exception {

        String accountId = jwtUtil.getSubjectFromAuthHeaderWithoutAuth(authorizationHeader);
        MemberStatusResponseDTO memberStatusResponseDTO = memberService.rejectSupplyByMemberId(memberId, accountId);
        return memberStatusResponseDTO != null ?
                ResponseEntity.ok(memberStatusResponseDTO) :
                ResponseEntity.notFound().build();
    }

    @Operation(summary = "팀에서 멤버 제거 ", description = "길드에서 멤버를 제거합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멤버가 성공적으로 제거되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @DeleteMapping("/{memberId}")
    public ResponseEntity<MemberStatusResponseDTO> removeMemberFromTeam(
            @PathVariable(name = "memberId") Long memberId) {

        MemberStatusResponseDTO memberStatusResponseDTO = memberService.deleteMemberByMemberId(memberId);
        return memberStatusResponseDTO != null ?
                ResponseEntity.ok(memberStatusResponseDTO) :
                ResponseEntity.notFound().build();
    }
}

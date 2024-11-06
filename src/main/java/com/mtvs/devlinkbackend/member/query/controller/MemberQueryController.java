package com.mtvs.devlinkbackend.member.query.controller;

import com.mtvs.devlinkbackend.member.query.service.MemberViewService;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberQueryController {

    private final MemberViewService memberViewService;

    public MemberQueryController(MemberViewService memberViewService) {
        this.memberViewService = memberViewService;
    }

    @Operation(summary = "멤버 정보 조회", description = "지원한 멤버의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "팀을 찾을 수 없습니다.")
    })
    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberStatusResponseDTO> updateTeam(
            @PathVariable(name = "memberId") Long memberId) {

        MemberStatusResponseDTO memberStatusResponseDTO = memberViewService.findMemberByMemberId(memberId);
        return memberStatusResponseDTO != null ?
                ResponseEntity.ok(memberStatusResponseDTO) :
                ResponseEntity.notFound().build();
    }
}

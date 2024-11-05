package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.member.command.model.entity.AcceptStatus;
import com.mtvs.devlinkbackend.member.command.model.entity.Member;
import com.mtvs.devlinkbackend.member.command.repository.MemberRepository;
import com.mtvs.devlinkbackend.member.command.service.MemberService;
import com.mtvs.devlinkbackend.member.query.repository.MemberViewRepository;
import com.mtvs.devlinkbackend.member.query.service.MemberViewService;
import com.mtvs.devlinkbackend.member.query.view.response.MemberStatusResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MemberCRUDTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberViewRepository memberViewRepository;

    @InjectMocks
    private MemberService memberService;

    @InjectMocks
    private MemberViewService memberViewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegistAll() {
        // given
        List<Member> memberList = Arrays.asList(new Member(), new Member());

        // when
        memberService.registAll(memberList);

        // then
        verify(memberRepository, times(1)).saveAll(memberList);
    }

    @Test
    public void testDeleteAll() {
        // given
        List<Long> memberIdList = Arrays.asList(1L, 2L, 3L);

        // when
        memberService.deleteAll(memberIdList);

        // then
        verify(memberRepository, times(1)).deleteMembersByMemberIdIn(memberIdList);
    }

    @Test
    public void testAcceptSupplyByMemberId() {
        // given
        Long memberId = 1L;
        Member member = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        MemberStatusResponseDTO responseDTO = memberService.acceptSupplyByMemberId(memberId);

        // then
        assertNotNull(responseDTO);
        assertEquals(AcceptStatus.ACCEPTED, responseDTO.getData().getIsAccepted());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    public void testRejectSupplyByMemberId() {
        // given
        Long memberId = 1L;
        Member member = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        MemberStatusResponseDTO responseDTO = memberService.rejectSupplyByMemberId(memberId);

        // then
        assertNotNull(responseDTO);
        assertEquals(AcceptStatus.REJECTED, responseDTO.getData().getIsAccepted());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    public void testDeleteMemberByMemberId() {
        // given
        Long memberId = 1L;
        Member member = new Member();
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        MemberStatusResponseDTO responseDTO = memberService.deleteMemberByMemberId(memberId);

        // then
        assertNotNull(responseDTO);
        assertEquals(AcceptStatus.DELETED, responseDTO.getData().getIsAccepted());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    public void testFindMemberByMemberId() {
        // given
        Long memberId = 1L;
        Member member = new Member();
        when(memberViewRepository.findById(memberId)).thenReturn(Optional.of(member));

        // when
        MemberStatusResponseDTO responseDTO = memberViewService.findMemberByMemberId(memberId);

        // then
        assertNotNull(responseDTO);
        verify(memberViewRepository, times(1)).findById(memberId);
    }
}

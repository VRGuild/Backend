package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.comment.dto.request.CommentRegistRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.request.CommentUpdateRequestDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentListResponseDTO;
import com.mtvs.devlinkbackend.comment.dto.response.CommentSingleResponseDTO;
import com.mtvs.devlinkbackend.comment.entity.Comment;
import com.mtvs.devlinkbackend.comment.repository.CommentRepository;
import com.mtvs.devlinkbackend.comment.repository.CommentViewRepository;
import com.mtvs.devlinkbackend.comment.service.CommentService;
import com.mtvs.devlinkbackend.comment.service.CommentViewService;
import com.mtvs.devlinkbackend.user.command.model.entity.User;
import com.mtvs.devlinkbackend.user.query.service.UserViewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class CommentCRUDTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserViewService userViewService;

    @InjectMocks
    private CommentService commentService;

    @InjectMocks
    private CommentViewService commentViewService;

    @Mock
    private CommentViewRepository commentViewRepository;

    @Test
    @DisplayName("코멘트를 등록하는 테스트")
    void registCommentTest() {
        CommentRegistRequestDTO requestDTO = new CommentRegistRequestDTO(1L, "테스트 내용");
        Comment comment = new Comment("테스트 내용", 1L);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentSingleResponseDTO responseDTO = commentService.registComment(requestDTO);

        assertEquals("테스트 내용", responseDTO.getData().getContent());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("코멘트를 수정하는 테스트")
    void updateCommentTest() {
        CommentUpdateRequestDTO requestDTO = new CommentUpdateRequestDTO(1L, "수정된 내용");
        Comment comment = new Comment("기존 내용", 1L);
        User user = new User();
        user.setUserId(1L);

        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(userViewService.findUserByEpicAccountId(anyString())).thenReturn(user);

        CommentSingleResponseDTO responseDTO = commentService.updateComment(requestDTO, "testAccount");

        assertEquals("수정된 내용", responseDTO.getData().getContent());
        verify(commentRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("코멘트를 삭제하는 테스트")
    void deleteCommentTest() {
        Long commentId = 1L;

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    @DisplayName("코멘트 ID로 코멘트를 조회하는 테스트")
    void findCommentByCommentIdTest() {
        Long commentId = 1L;
        Comment comment = mock(Comment.class); // 모킹된 Comment 객체 사용

        when(commentViewRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(comment.getContent()).thenReturn("테스트 내용"); // 모킹된 객체의 메서드를 호출

        CommentSingleResponseDTO responseDTO = commentViewService.findCommentByCommentId(commentId);

        assertEquals("테스트 내용", responseDTO.getData().getContent());
        verify(commentViewRepository, times(1)).findById(commentId);
    }

    @Test
    @DisplayName("계정 ID로 코멘트 리스트를 조회하는 테스트")
    void findCommentsByAccountIdTest() {
        User user = mock(User.class); // 모킹된 User 객체 사용
        when(user.getUserId()).thenReturn(1L);

        Comment comment1 = mock(Comment.class); // 모킹된 Comment 객체 사용
        Comment comment2 = mock(Comment.class); // 모킹된 Comment 객체 사용
        when(comment1.getContent()).thenReturn("테스트 내용1");
        when(comment2.getContent()).thenReturn("테스트 내용2");

        List<Comment> comments = Arrays.asList(comment1, comment2);

        when(userViewService.findUserByEpicAccountId(anyString())).thenReturn(user);
        when(commentViewRepository.findAllByUserId(1L)).thenReturn(comments);

        CommentListResponseDTO responseDTO = commentViewService.findCommentsByAccountId("testAccount");

        assertEquals(2, responseDTO.getData().size());
        verify(commentViewRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    @DisplayName("코멘트 ID 리스트로 코멘트를 조회하는 테스트")
    void findCommentsByCommentIdListTest() {
        List<Long> commentIdList = Arrays.asList(1L, 2L);
        Comment comment1 = mock(Comment.class); // 모킹된 Comment 객체 사용
        Comment comment2 = mock(Comment.class); // 모킹된 Comment 객체 사용

        List<Comment> comments = Arrays.asList(comment1, comment2);

        when(commentViewRepository.findByCommentIdIn(commentIdList)).thenReturn(comments);

        CommentListResponseDTO responseDTO = commentViewService.findCommentsByCommentIdList(commentIdList);

        assertEquals(2, responseDTO.getData().size());
        verify(commentViewRepository, times(1)).findByCommentIdIn(commentIdList);
    }
}

package com.mtvs.devlinkbackend.crud;

import com.mtvs.devlinkbackend.channel.dto.request.MemoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.IsSuccessDTO;
import com.mtvs.devlinkbackend.channel.dto.response.MemoResponseDTO;
import com.mtvs.devlinkbackend.channel.entity.MemoInfo;
import com.mtvs.devlinkbackend.channel.entity.Position;
import com.mtvs.devlinkbackend.channel.repository.MemoRepository;
import com.mtvs.devlinkbackend.channel.repository.MemoViewRepository;
import com.mtvs.devlinkbackend.channel.service.MemoService;
import com.mtvs.devlinkbackend.channel.service.MemoViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MemoCRUDTest {
    @Autowired
    private MemoRepository memoRepository;

    @Autowired
    private MemoViewRepository memoViewRepository;

    @Autowired
    private MemoService memoService;

    @Autowired
    private MemoViewService memoViewService;

    @Test
    void TestRegistMemo() {
        // 1. 메모 등록을 위한 DTO 생성
        MemoRegistDTO dto = new MemoRegistDTO("메모 텍스트", new Position(1.0F, 2.0F, 3.0F));

        // 2. 메모 등록 서비스 호출
        // channelId를 "channel 1"로 설정하고, 메모 등록을 시도
        IsSuccessDTO isSuccessDTO = memoService.registMemo(dto, "channel 1");

        // 3. 등록 성공 여부 확인
        assertThat(isSuccessDTO.isSuccess()).isTrue(); // 메모가 성공적으로 등록되었는지 검증
    }

    @Test
    void TestReadMemo() {
        // 1. 사용할 channelId 설정
        String channelId = "test-channel";

        // 2. 여러 개의 원본 데이터 생성 및 등록
        for (int i = 0; i < 5; i++) {
            MemoRegistDTO registDTO = new MemoRegistDTO("메모 텍스트 " + (i + 1), new Position(1.0F + i, 2.0F + i, 3.0F + i));
            IsSuccessDTO isSuccessRegist = memoService.registMemo(registDTO, channelId); // channelId를 경로 매개변수로 전달
            assertThat(isSuccessRegist.isSuccess()).isTrue(); // 등록 성공 여부 확인
        }

        // 3. 메모 조회 서비스 호출
        MemoResponseDTO memoResponseDTO = memoViewService.getMemo(channelId);

        // 4. 조회된 메모 확인
        List<MemoInfo> memoInfoList = memoResponseDTO.getMemoInfoList();
        assertThat(memoInfoList).isNotEmpty(); // 조회된 메모 리스트가 비어있지 않아야 함
        assertThat(memoInfoList.size()).isEqualTo(5); // 등록한 메모의 수와 일치해야 함

        // 5. 각 메모의 텍스트 및 위치 확인
        for (int i = 0; i < memoInfoList.size(); i++) {
            assertThat(memoInfoList.get(i).getMemoText()).isEqualTo("메모 텍스트 " + (i + 1)); // 텍스트 확인
            assertThat(memoInfoList.get(i).getPosition()).isEqualTo(new Position(1.0F + i, 2.0F + i, 3.0F + i)); // 위치 확인
        }
    }

    @Test
    void TestModifyMemo() {
        // 1. 원본 데이터 생성 및 등록
        MemoRegistDTO registDTO = new MemoRegistDTO("원본 메모 텍스트", new Position(1.0F, 2.0F, 3.0F));
        IsSuccessDTO isSuccessRegist = memoService.registMemo(registDTO, "1");
        assertThat(isSuccessRegist.isSuccess()).isTrue(); // 등록 성공 여부 확인

        // 2. 등록된 메모 조회
        MemoInfo savedMemo = memoRepository.findAll().get(0); // 가장 최근에 등록된 메모 조회 (단일 테스트에서는 이 방법이 유효)

        // 3. 수정할 데이터 준비
        MemoRegistDTO dto = new MemoRegistDTO("수정된 메모 텍스트", new Position(4.0F, 5.0F, 6.0F));

        // 4. 메모 수정
        IsSuccessDTO isSuccessDTO = memoService.modifyMemo(dto, "1");
        assertThat(isSuccessDTO.isSuccess()).isTrue();

        // 5. 수정된 메모 확인
        Optional<MemoInfo> optionalUpdatedMemo = memoRepository.findById(savedMemo.getMemoId());
        assertThat(optionalUpdatedMemo).isPresent(); // 메모가 존재해야 함
        assertThat(optionalUpdatedMemo.get().getMemoText()).isEqualTo("수정된 메모 텍스트"); // 수정된 텍스트 확인
        assertThat(optionalUpdatedMemo.get().getPosition()).isEqualTo(new Position(4.0F, 5.0F, 6.0F)); // 수정된 위치 확인
    }
    @Test
    void TestDeleteMemo() {
        // 1. 사용할 channelId 설정
        String channelId = "channel 1";

        // 2. 메모 등록을 위한 DTO 생성
        MemoRegistDTO dto = new MemoRegistDTO("삭제할 메모 텍스트", new Position(1.0F, 2.0F, 3.0F));

        // 3. 메모 등록 서비스 호출
        IsSuccessDTO isSuccessRegist = memoService.registMemo(dto, channelId);
        assertThat(isSuccessRegist.isSuccess()).isTrue(); // 메모가 성공적으로 등록되었는지 확인

        // 4. 등록된 메모 조회
        MemoResponseDTO memoResponseDTO = memoViewService.getMemo(channelId);
        List<MemoInfo> memoInfoList = memoResponseDTO.getMemoInfoList();
        assertThat(memoInfoList).isNotEmpty(); // 조회된 메모 리스트가 비어있지 않아야 함
        assertThat(memoInfoList.size()).isEqualTo(1); // 등록한 메모 수 확인

        // 5. 메모 삭제 서비스 호출
        String memoIdToDelete = memoInfoList.get(0).getMemoId(); // 삭제할 메모의 ID 가져오기
        IsSuccessDTO isSuccessDelete = memoService.deleteMemoById(memoIdToDelete); // 메모 삭제 호출
        assertThat(isSuccessDelete.isSuccess()).isTrue(); // 삭제 성공 여부 확인

        // 6. 삭제 후 메모 조회하여 확인
        MemoResponseDTO afterDeleteResponseDTO = memoViewService.getMemo(channelId);
        List<MemoInfo> afterDeleteMemoInfoList = afterDeleteResponseDTO.getMemoInfoList();
        assertThat(afterDeleteMemoInfoList).isEmpty(); // 삭제 후 메모 리스트는 비어있어야 함
    }
}

package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.dto.request.MemoRegistDTO;
import com.mtvs.devlinkbackend.channel.dto.response.IsSuccessDTO;
import com.mtvs.devlinkbackend.channel.entity.MemoInfo;
import com.mtvs.devlinkbackend.channel.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemoService {
    @Autowired
    private MemoRepository memoRepository;

    @Transactional
    public IsSuccessDTO registMemo(MemoRegistDTO memoRegistDTO, String channelId) {
        IsSuccessDTO isSuccessDTO = new IsSuccessDTO();

        try {
            MemoInfo memo = new MemoInfo();
            memo.setMemoText(memoRegistDTO.getMemoText());
            memo.setPosition(memoRegistDTO.getPosition());
            memo.setChannelId(channelId);
            memoRepository.save(memo);
            isSuccessDTO.setSuccess(true);
            isSuccessDTO.setMessage("메모 등록 성공");
        } catch (Exception e) {
            isSuccessDTO.setSuccess(false);
            isSuccessDTO.setMessage("메모 등록 실패");
            e.printStackTrace();
        }
        return isSuccessDTO;
    }

    @Transactional
    public IsSuccessDTO modifyMemo(MemoRegistDTO memoRegistDTO, String memoId) {
        IsSuccessDTO isSuccessDTO = new IsSuccessDTO();
        try {
            MemoInfo memo = memoRepository.findById(memoId)
                    .orElseThrow(() -> new RuntimeException("메모를 찾을 수 없습니다."));
            memo.setMemoText(memoRegistDTO.getMemoText());
            memo.setPosition(memoRegistDTO.getPosition());
            memoRepository.save(memo);

            isSuccessDTO.setSuccess(true);
            isSuccessDTO.setMessage("메모 수정 성공");
        } catch (Exception e) {
            isSuccessDTO.setSuccess(false);
            isSuccessDTO.setMessage("메모 수정 실패");
            e.printStackTrace();
        }
        return isSuccessDTO;
    }

    @Transactional
    public IsSuccessDTO deleteMemoById(String memoId) {
        IsSuccessDTO isSuccessDTO = new IsSuccessDTO();
        try {
            memoRepository.deleteById(memoId);
            isSuccessDTO.setSuccess(true);
            isSuccessDTO.setMessage("메모 삭제 성공");
        } catch (Exception e) {
            isSuccessDTO.setSuccess(false);
            isSuccessDTO.setMessage("메모 삭제 실패");
            e.printStackTrace();
        }
        return isSuccessDTO;
    }

    @Transactional
    public void deleteMemosByChannelId(String channelId) {
        memoRepository.deleteAllByChannelId(channelId);
    }
}

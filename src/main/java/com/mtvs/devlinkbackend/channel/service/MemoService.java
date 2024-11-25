package com.mtvs.devlinkbackend.channel.service;

import com.mtvs.devlinkbackend.channel.repository.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemoService {
    @Autowired
    private MemoRepository memoRepository;


}

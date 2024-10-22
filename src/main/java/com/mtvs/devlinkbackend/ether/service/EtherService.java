package com.mtvs.devlinkbackend.ether.service;

import com.mtvs.devlinkbackend.ether.dto.response.EtherListResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.request.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.response.EtherSingleResponseDTO;
import com.mtvs.devlinkbackend.ether.dto.request.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.repository.EtherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class EtherService {
    private final EtherRepository etherRepository;

    public EtherService(EtherRepository etherRepository) {
        this.etherRepository = etherRepository;
    }

    @Transactional
    public EtherSingleResponseDTO registEther(EtherRegistRequestDTO etherRegistRequestDTO, String accountId) {
        return new EtherSingleResponseDTO(etherRepository.save(
                new Ether(
                        accountId,
                        etherRegistRequestDTO.getReason(),
                        etherRegistRequestDTO.getAmount())
        ));
    }

    public EtherSingleResponseDTO findEtherByEtherId(Long etherId) {
        return new EtherSingleResponseDTO(etherRepository.findById(etherId).orElse(null));
    }

    public EtherListResponseDTO findEthersByAccountId(String accountId) {
        return new EtherListResponseDTO(etherRepository.findEthersByAccountId(accountId));
    }

    public EtherListResponseDTO findEthersByReason(String reason) {
        return new EtherListResponseDTO(etherRepository.findEthersByReason(reason));
    }

    @Transactional
    public EtherSingleResponseDTO updateEther(EtherUpdateRequestDTO etherUpdateRequestDTO) {
        Optional<Ether> ether = etherRepository.findById(etherUpdateRequestDTO.getEtherId());
        if(ether.isPresent()) {
            Ether foundEther = ether.get();
            foundEther.setReason(etherUpdateRequestDTO.getReason());
            foundEther.setAmount(etherUpdateRequestDTO.getAmount());
            return new EtherSingleResponseDTO(foundEther);
        } else
            throw new IllegalArgumentException("잘못된 Ether Id로 호출, ETHER_ID : " + etherUpdateRequestDTO.getEtherId());
    }

    public void deleteEtherByEtherId(Long etherId) {
        etherRepository.deleteById(etherId);
    }
}

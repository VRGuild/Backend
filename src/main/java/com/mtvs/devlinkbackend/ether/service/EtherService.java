package com.mtvs.devlinkbackend.ether.service;

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
    public EtherSingleResponseDTO registEther(EtherRegistRequestDTO etherRegistRequestDTO) {
        return new EtherSingleResponseDTO(etherRepository.save(
                new Ether(
                        etherRegistRequestDTO.getUserId(),
                        etherRegistRequestDTO.getCause(),
                        etherRegistRequestDTO.getGoldAmount(),
                        etherRegistRequestDTO.getSilverAmount())));
    }

    @Transactional
    public EtherSingleResponseDTO updateEther(EtherUpdateRequestDTO etherUpdateRequestDTO) {
        Optional<Ether> ether = etherRepository.findById(etherUpdateRequestDTO.getEtherId());
        if(ether.isPresent()) {
            Ether foundEther = ether.get();
            foundEther.setCause(etherUpdateRequestDTO.getCause());
            foundEther.setGoldAmount(etherUpdateRequestDTO.getGoldAmount());
            foundEther.setSilverAmount(etherUpdateRequestDTO.getSilverAmount());
            return new EtherSingleResponseDTO(foundEther);
        } else
            throw new IllegalArgumentException("잘못된 Ether Id로 호출, ETHER_ID : " + etherUpdateRequestDTO.getEtherId());
    }

    public void deleteEtherByEtherId(Long etherId) {
        etherRepository.deleteById(etherId);
    }
}

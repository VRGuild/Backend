package com.mtvs.devlinkbackend.ether.service;

import com.mtvs.devlinkbackend.ether.dto.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.repository.EtherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EtherService {
    private final EtherRepository etherRepository;

    public EtherService(EtherRepository etherRepository) {
        this.etherRepository = etherRepository;
    }

    @Transactional
    public Ether registEther(EtherRegistRequestDTO etherRegistRequestDTO, String accountId) {
        return etherRepository.save(
                new Ether(
                        accountId,
                        etherRegistRequestDTO.getReason(),
                        etherRegistRequestDTO.getAmount())
        );
    }

    public Ether findEtherByEtherId(Long etherId) {
        return etherRepository.findById(etherId).orElse(null);
    }

    public List<Ether> findEthersByAccountId(String accountId) {
        return etherRepository.findEthersByAccountId(accountId);
    }

    public List<Ether> findEthersByReason(String reason) {
        return etherRepository.findEthersByReason(reason);
    }

    @Transactional
    public Ether updateEther(EtherUpdateRequestDTO etherUpdateRequestDTO) {
        Optional<Ether> ether = etherRepository.findById(etherUpdateRequestDTO.getEtherId());
        if(ether.isPresent()) {
            Ether foundEther = ether.get();
            foundEther.setReason(etherUpdateRequestDTO.getReason());
            foundEther.setAmount(etherUpdateRequestDTO.getAmount());
            return foundEther;
        } else
            throw new IllegalArgumentException("잘못된 Ether Id로 호출, ETHER_ID : " + etherUpdateRequestDTO.getEtherId());
    }

    public void deleteEtherByEtherId(Long etherId) {
        etherRepository.deleteById(etherId);
    }
}

package com.mtvs.devlinkbackend.ether.repository;

import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.repository.projection.EtherGoldAndSilver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtherViewRepository extends JpaRepository<Ether, Long> {
    Page<Ether> findAllByUserId(Long userId, Pageable pageable);
    List<EtherGoldAndSilver> findEthersByUserId(Long userId);
}

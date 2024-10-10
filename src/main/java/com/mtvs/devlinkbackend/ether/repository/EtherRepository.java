package com.mtvs.devlinkbackend.ether.repository;

import com.mtvs.devlinkbackend.ether.entity.Ether;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtherRepository extends JpaRepository<Ether, Long> {
    public List<Ether> findEthersByAccountId(String accountId);
    public List<Ether> findEthersByReason(String reason);
}

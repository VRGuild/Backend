package com.mtvs.devlinkbackend.ether.controller;

import com.mtvs.devlinkbackend.config.JwtUtil;
import com.mtvs.devlinkbackend.ether.dto.EtherRegistRequestDTO;
import com.mtvs.devlinkbackend.ether.dto.EtherUpdateRequestDTO;
import com.mtvs.devlinkbackend.ether.entity.Ether;
import com.mtvs.devlinkbackend.ether.service.EtherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ether")
public class EtherController {
    private final EtherService etherService;
    private final JwtUtil jwtUtil;

    public EtherController(EtherService etherService, JwtUtil jwtUtil) {
        this.etherService = etherService;
        this.jwtUtil = jwtUtil;
    }

    // Create
    @PostMapping
    public ResponseEntity<Ether> registEther(
            @RequestBody EtherRegistRequestDTO etherRegistRequestDTO,
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(authorizationHeader);
        Ether newEther = etherService.registEther(etherRegistRequestDTO, accountId);
        return ResponseEntity.ok(newEther);
    }

    // Read - Find by Ether ID
    @GetMapping("/{etherId}")
    public ResponseEntity<Ether> findEtherByEtherId(@PathVariable Long etherId) {
        Ether ether = etherService.findEtherByEtherId(etherId);
        return ether != null ? ResponseEntity.ok(ether) : ResponseEntity.notFound().build();
    }

    // Read - Find by Account ID
    @GetMapping("/account")
    public ResponseEntity<List<Ether>> findEthersByAccountId(
            @RequestHeader(name = "Authorization") String authorizationHeader) throws Exception {

        String accountId = jwtUtil.getSubjectFromTokenWithoutAuth(authorizationHeader);
        List<Ether> ethers = etherService.findEthersByAccountId(accountId);
        return ResponseEntity.ok(ethers);
    }

    // Read - Find by Reason
    @GetMapping("/reason/{reason}")
    public ResponseEntity<List<Ether>> findEthersByReason(@PathVariable String reason) {
        List<Ether> ethers = etherService.findEthersByReason(reason);
        return ResponseEntity.ok(ethers);
    }

    // Update
    @PutMapping
    public ResponseEntity<Ether> updateEther(@RequestBody EtherUpdateRequestDTO etherUpdateRequestDTO) {
        try {
            Ether updatedEther = etherService.updateEther(etherUpdateRequestDTO);
            return ResponseEntity.ok(updatedEther);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Delete
    @DeleteMapping("/{etherId}")
    public ResponseEntity<Void> deleteEtherByEtherId(@PathVariable Long etherId) {
        etherService.deleteEtherByEtherId(etherId);
        return ResponseEntity.noContent().build();
    }
}

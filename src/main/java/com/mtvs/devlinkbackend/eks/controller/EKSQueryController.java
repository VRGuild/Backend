package com.mtvs.devlinkbackend.eks.controller;

import com.mtvs.devlinkbackend.eks.service.KubernetesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eks/test")
public class EKSQueryController {

    private final KubernetesService kubernetesService;

    public EKSQueryController(KubernetesService kubernetesService) {
        this.kubernetesService = kubernetesService;
    }

    @GetMapping("/node-ports")
    public List<String> getNodePorts(@RequestParam String namespace) throws Exception {
        return kubernetesService.getNodePorts(namespace);
    }
}

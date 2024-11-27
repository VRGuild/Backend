package com.mtvs.devlinkbackend.eks.service;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ServiceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class KubernetesService {

    private final CoreV1Api coreV1Api;

    @Autowired
    public KubernetesService(ApiClient apiClient) {
        this.coreV1Api = new CoreV1Api(apiClient);
    }

    public List<Map<String, Object>> getNodePorts(String namespace) throws Exception {
        try {
            V1ServiceList serviceList = coreV1Api.listNamespacedService(namespace).execute();

            return serviceList.getItems().stream()
                    .filter(service -> "NodePort".equals(service.getSpec().getType()) || "LoadBalancer".equals(service.getSpec().getType()))
                    .filter(service -> service.getSpec() != null && service.getSpec().getPorts() != null)
                    .flatMap(service -> service.getSpec().getPorts().stream()
                            .filter(port -> port.getNodePort() != null && port.getNodePort() > 0)
                            .map(port -> {
                                Map<String, Object> map = new HashMap<>();
                                map.put("Service", service.getMetadata().getName());
                                map.put("Port", port.getPort());
                                map.put("NodePort", port.getNodePort());
                                return map;
                            }))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to retrieve NodePorts for namespace: " + namespace, e);
        }
    }
}

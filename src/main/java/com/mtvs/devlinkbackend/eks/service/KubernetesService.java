package com.mtvs.devlinkbackend.eks.service;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ServiceList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KubernetesService {

    private final CoreV1Api coreV1Api;

    @Autowired
    public KubernetesService(ApiClient apiClient) {
        this.coreV1Api = new CoreV1Api(apiClient);
    }

    public List<String> getNodePorts(String namespace) throws Exception {
        // Retrieve the list of services in the specified namespace
        V1ServiceList serviceList = coreV1Api.listNamespacedService(namespace).limit(100).execute();

        // Extract and format the NodePort information
        return serviceList.getItems().stream()
                .flatMap(service -> service.getSpec().getPorts().stream()
                        .filter(port -> port.getNodePort() != null && port.getNodePort() > 0)
                        .map(port -> String.format("Service: %s, Port: %d, NodePort: %d",
                                service.getMetadata().getName(), port.getPort(), port.getNodePort())))
                .collect(Collectors.toList());
    }
}

package org.finora.demoapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BasicApiController {

    private final String serviceName;
    private final String instanceId;

    public BasicApiController(
            @Value("${spring.application.name}") String serviceName,
            @Value("${app.instance-id}") String instanceId) {
        this.serviceName = serviceName;
        this.instanceId = instanceId;
    }

    @GetMapping("/hello")
    public ApiResponse hello() {
        return new ApiResponse(serviceName, instanceId, "Hello from " + serviceName);
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP", serviceName, instanceId);
    }

    public record ApiResponse(String service, String instanceId, String message) {
    }

    public record HealthResponse(String status, String service, String instanceId) {
    }
}

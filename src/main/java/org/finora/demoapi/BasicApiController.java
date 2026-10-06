package org.finora.demoapi;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicApiController {

    private final String serviceName;
    private final String instanceId;
    private final String hostName;

    public BasicApiController(
            @Value("${spring.application.name}") String serviceName,
            @Value("${app.instance-id}") String instanceId,
            @Value("${HOSTNAME:local}") String hostName) {
        this.serviceName = serviceName;
        this.instanceId = instanceId;
        this.hostName = hostName;
    }

    @GetMapping("/")
    public InstanceInfo instanceInfo() throws SocketException {
        List<HostAddress> ipAddresses = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces != null && interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress address = addresses.nextElement();
                ipAddresses.add(new HostAddress(
                        networkInterface.getName(),
                        address.getHostAddress(),
                        address.isLoopbackAddress()));
            }
        }
        return new InstanceInfo(
                serviceName,
                instanceId,
                hostName,
                ipAddresses,
                System.getProperty("os.name"),
                System.getProperty("os.arch"),
                System.getProperty("java.version"),
                Runtime.getRuntime().availableProcessors());
    }

    @GetMapping("/api/hello")
    public ApiResponse hello() {
        return new ApiResponse(serviceName, instanceId, "Hello from " + serviceName);
    }

    @GetMapping("/api/health")
    public HealthResponse health() {
        return new HealthResponse("UP", serviceName, instanceId);
    }

    public record ApiResponse(String service, String instanceId, String message) {
    }

    public record HealthResponse(String status, String service, String instanceId) {
    }

    public record InstanceInfo(
            String service,
            String instanceId,
            String hostName,
            List<HostAddress> ipAddresses,
            String operatingSystem,
            String architecture,
            String javaVersion,
            int availableProcessors) {
    }

    public record HostAddress(String interfaceName, String ipAddress, boolean loopback) {
    }
}

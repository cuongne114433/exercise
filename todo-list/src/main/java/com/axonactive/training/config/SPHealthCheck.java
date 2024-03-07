package com.axonactive.training.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;

@Startup
@ApplicationScoped
public class SPHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        // Logic to check external service health
        boolean externalServiceHealthy = true; // Placeholder implementation
        if (externalServiceHealthy) {
            return HealthCheckResponse.up("External Service Health Check");
        } else {
            return HealthCheckResponse.down("External Service Health Check");
        }
    }
}

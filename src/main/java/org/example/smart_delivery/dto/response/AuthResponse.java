package org.example.smart_delivery.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}

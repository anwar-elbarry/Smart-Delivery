package org.example.smart_delivery.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken,
        UserRespDTO user
) {
}

package com.meows.meows.dto.register;

import lombok.Builder;

@Builder
public record RegisterResponse(String token) {
}

package com.meows.meows.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {
}

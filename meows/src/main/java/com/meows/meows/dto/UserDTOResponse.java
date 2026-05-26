package com.meows.meows.dto;

/* template DTOResponse */

import lombok.Builder;

@Builder
public record UserDTOResponse(Long id,
                              String nome) {
}

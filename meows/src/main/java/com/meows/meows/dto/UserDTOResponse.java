package com.meows.meows.dto;

/* template DTOResponse */

// TODO implement validation

import lombok.Builder;

@Builder
public record UserDTOResponse(Long id,
                              String nome) {
}

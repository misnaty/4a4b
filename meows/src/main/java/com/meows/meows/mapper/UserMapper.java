package com.meows.meows.mapper;

/* template Mapper */

import com.meows.meows.dto.UserDTORequest;
import com.meows.meows.dto.UserDTOResponse;
import com.meows.meows.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserDTORequest userRequest){
        return UserEntity
                .builder()
                .nome(userRequest.nome())
                .senha(userRequest.senha())
                .build();
    }

    public UserDTOResponse toResponse(UserEntity userEntity){
        return UserDTOResponse
                .builder()
                .id(userEntity.getId())
                .nome(userEntity.getNome())
                .build();
    }
}

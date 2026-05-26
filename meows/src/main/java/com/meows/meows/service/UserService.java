package com.meows.meows.service;
/* template CRUD */

import com.meows.meows.dto.UserDTORequest;
import com.meows.meows.dto.UserDTOResponse;
import com.meows.meows.entity.UserEntity;
import com.meows.meows.mapper.UserMapper;
import com.meows.meows.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDTOResponse> listarUsers(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserDTOResponse listarUsersById(Long id){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return userMapper.toResponse(userEntity);
    }

    public UserDTOResponse createUsers(UserDTORequest userRequest){
        return userMapper.toResponse(userRepository
                .save(userMapper
                        .toEntity(userRequest)));
    }

    public UserDTOResponse updateUsers(Long id, UserDTORequest userRequest){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("teste"));

        userEntity.setNome(userRequest.nome());
        userEntity.setSenha(userRequest.senha());

        UserEntity userUpdated = userRepository.save(userEntity);

        return userMapper.toResponse(userUpdated);
    }

    public void deleteUsers(Long id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        userRepository.delete(userEntity);
    }
}

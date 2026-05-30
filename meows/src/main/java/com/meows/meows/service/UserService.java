package com.meows.meows.service;
/* template CRUD */

import com.meows.meows.config.TokenConfig;
import com.meows.meows.dto.LoginRequest;
import com.meows.meows.dto.LoginResponse;
import com.meows.meows.dto.UserDTORequest;
import com.meows.meows.dto.UserDTOResponse;
import com.meows.meows.dto.register.RegisterRequest;
import com.meows.meows.dto.register.RegisterResponse;
import com.meows.meows.entity.UserEntity;
import com.meows.meows.mapper.UserMapper;
import com.meows.meows.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenConfig tokenConfig;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, TokenConfig tokenConfig, AuthenticationManager authenticationManager){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenConfig = tokenConfig;
        this.authenticationManager = authenticationManager;
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



    /* AuthController methods */

    // Method and mapper for register user (userdto->user)
    public RegisterResponse registerUser(RegisterRequest registerRequest){

        if(!registerRequest.senha().equals(registerRequest.confirmarSenha())){
            throw new RuntimeException("Senhas diferentes");
        }

        if(userRepository.findByNome(registerRequest.nome()).isPresent()){
            throw new RuntimeException("Usuário existente");
        }



        UserEntity userEntity = userMapper.registerToEntity(registerRequest);

        userEntity.setSenha(passwordEncoder.encode(registerRequest.senha()));

        UserEntity savedUser = userRepository.save(userEntity);

        String token = tokenConfig.generateToken(savedUser);

        return RegisterResponse
                .builder()
                .token(token)
                .build();
    }

    // Method and mapper for register user (userdto->user)
    public LoginResponse loginUser(LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.nome(), loginRequest.senha())
        );

        UserEntity userEntity =
                (UserEntity) authentication.getPrincipal();

        if (userEntity == null) {
            throw new IllegalStateException("Usuário autenticado é nulo");
        }

        String token = tokenConfig.generateToken(userEntity);

        return LoginResponse
                .builder()
                .token(token)
                .build();
    }
}

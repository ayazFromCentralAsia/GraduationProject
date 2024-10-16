package com.example.delivery.service.Service;

import com.example.delivery.service.Config.JWT.JWTService;
import com.example.delivery.service.Dto.User.UserSignUpRequest;
import com.example.delivery.service.Dto.User.UserSingInRequest;
import com.example.delivery.service.Dto.User.UserSingInResponse;
import com.example.delivery.service.Entities.User;
import com.example.delivery.service.Enum.Role;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Exceptions.NotFoundException;
import com.example.delivery.service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;
    private final JWTService jwtService;

    ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public void createUser(UserSignUpRequest userSignUpRequest) {
        try {
            if (userRepository.existsByUsername(userSignUpRequest.getUsername())){
                logger.info("User with this username already exists: " + userSignUpRequest.getUsername());
                throw new FailedCreateException("FailedCreate.userAlreadyExists");
            }
            logger.info("Creating user with username: " + userSignUpRequest.getUsername());
            if (userRepository.existsByNumber(userSignUpRequest.getPhone())){
                logger.info("User with phone number already exists: " + userSignUpRequest.getPhone());
                throw new FailedCreateException("FailedCreate.userAlreadyExists");
            }
            if (userRepository.existsByEmail(userSignUpRequest.getEmail())){
                logger.info("User with email already exists: " + userSignUpRequest.getEmail());
                throw new FailedCreateException("FailedCreate.userAlreadyExists");
            }
            User user = modelMapper.map(userSignUpRequest, User.class);
            user.setRole(Role.USER);
            userRepository.save(user);
            logger.info("User created successfully with username: " + userSignUpRequest.getUsername());
            logger.info("Token generated successfully for user with username: " + userSignUpRequest.getUsername());
        }catch (FailedCreateException | NotFoundException failedCreateException){
            logger.error(failedCreateException.getMessage());
            throw failedCreateException;
        }
    }
    public UserSingInResponse login(UserSingInRequest userSignInRequest) {
        if (userRepository.existsByUsername(userSignInRequest.getUsername())) {
            Optional<User> user = userRepository.findByUsername(userSignInRequest.getUsername());
            if (user.get().getPassword().equals(userSignInRequest.getPassword())) {
                logger.info("User logged in successfully with username: " + userSignInRequest.getUsername());
                String token = jwtService.generateToken(user.get());
                logger.info("Token generated successfully for user with username: " + userSignInRequest.getUsername());
                return UserSingInResponse.builder().token(token).role(user.get().getRole()).build();
            }
            logger.info("Failed to login user with username: " + userSignInRequest.getUsername());
        }
        logger.info("User with username: " + userSignInRequest.getUsername() + " not found");
        throw new NotFoundException("NotFound.user");
    }
}

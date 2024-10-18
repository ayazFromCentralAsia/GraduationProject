package com.example.delivery.service.Service;

import com.example.delivery.service.Dto.Couriers.CouriersDto;
import com.example.delivery.service.Dto.User.UserInfoResponse;
import com.example.delivery.service.Entities.Couriers;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Repository.CouriersRepository;
import com.example.delivery.service.Repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CouriersRepository couriersRepository;

    ModelMapper modelMapper = new ModelMapper();
    private final Logger logger = LoggerFactory.getLogger(AdminService.class);


    public List<UserInfoResponse> getAllUser(){
        try {
            logger.info("Getting all user");
            return userRepository.findAll()
                   .stream()
                   .map(user -> modelMapper.map(user, UserInfoResponse.class))
                   .toList();
        }catch (RuntimeException e){
            throw new RuntimeException("Error while getting all user: ", e);
        }
    }
    public void deleteUser(UUID id) {
        try {
            if (userRepository.existsById(id)) {
                logger.info("User with id: {} not found", id);
            }else {
            }
            userRepository.deleteById(id);
            logger.info("User with id: {} deleted successfully", id);
        }catch (RuntimeException e){
            throw new RuntimeException("Error while deleting user: ", e);
        }
    }

    public List<CouriersDto> getAllCouriers() {
        try {
            logger.info("Getting all couriers");
            return couriersRepository.findAll()
                   .stream()
                   .map(courier -> modelMapper.map(courier, CouriersDto.class))
                   .toList();
        }catch (RuntimeException e) {
            throw new RuntimeException("Error while getting all couriers: ", e);
        }
    }

    public ResponseEntity<?> createCourier(CouriersDto courierDto) {
        try {
            logger.info("Creating new courier");
            Couriers courier = modelMapper.map(courierDto, Couriers.class);
            couriersRepository.save(courier);
            return ResponseEntity.ok().build();
        }catch (FailedCreateException e) {
            throw new FailedCreateException("Error while creating new courier: ", e);
        }
    }

    public void changeCourierInfo(UUID id, CouriersDto couriersDto) {
        try {
            logger.info("Changing courier info with id: {}", id);
            Couriers courier = couriersRepository.findById(id).orElseThrow(() -> new RuntimeException("Courier not found"));
            courier.setUsername(couriersDto.getUsername());
            courier.setEmail(couriersDto.getEmail());
            courier.setPhone(couriersDto.getPhone());
            couriersRepository.save(courier);
            logger.info("Courier info with id: {} changed successfully", id);
        }catch (RuntimeException e) {
            throw new RuntimeException("Error while changing courier info: ", e);
        }
    }
}

package com.example.delivery.service.Service;

import com.example.delivery.service.Dto.Couriers.CouriersDto;
import com.example.delivery.service.Dto.User.UserInfoResponse;
import com.example.delivery.service.Dto.User.UserSingInRequest;
import com.example.delivery.service.Entities.Couriers;
import com.example.delivery.service.Entities.User;
import com.example.delivery.service.Enum.Role;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Exceptions.NotFoundException;
import com.example.delivery.service.Repository.CouriersRepository;
import com.example.delivery.service.Repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
                throw new RuntimeException("User with this name not Found, AdminService");
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
            if (couriersRepository.existsByEmail(courierDto.getEmail())){
                throw new FailedCreateException("Courier with this email already exists");
            }
            if (couriersRepository.existsByPhone(courierDto.getPhone())){
                throw new FailedCreateException("Courier with this phone number already exists");
            }
            logger.info("Creating new courier");
            Couriers courier = modelMapper.map(courierDto, Couriers.class);
            User newCourier = new User();
            newCourier.setEmail(courierDto.getEmail());
            newCourier.setNumber(courier.getPhone());
            newCourier.setUsername(courier.getUsername());
            newCourier.setRole(Role.COURIER);
            newCourier.setPassword(generatePassword());
            userRepository.save(newCourier);
            couriersRepository.save(courier);
            return ResponseEntity.ok().body("Courier Password: " + newCourier.getPassword());
        }catch (FailedCreateException e) {
            throw new FailedCreateException("Error while creating new courier: ", e);
        }
    }

    public static String generatePassword() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(Character::isLetterOrDigit)
                .build();
        return generator.generate(12);
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

    public void deleteCourier(UUID id) {
        try {
            if (couriersRepository.existsById(id)) {
                Optional<Couriers> couriers = couriersRepository.findById(id);
                Optional<User> user = userRepository.findByEmail(couriers.get().getEmail());
                userRepository.deleteById(user.get().getId());
                couriersRepository.deleteById(id);
                logger.info("Courier with id: {} deleted successfully", id);
            }else {
                logger.info("Courier with id: {} not found", id);
                throw new RuntimeException("Courier With this Id not Found");
            }
        }catch (RuntimeException e){
            throw new RuntimeException("Error while deleting Courier: ", e);
        }
    }
}

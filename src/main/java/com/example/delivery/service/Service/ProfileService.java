package com.example.delivery.service.Service;

import com.example.delivery.service.Config.JWT.JWTService;
import com.example.delivery.service.Dto.Address.AddressResponse;
import com.example.delivery.service.Dto.User.ProfileInfoResponse;
import com.example.delivery.service.Dto.User.UpdateProfileRequest;
import com.example.delivery.service.Entities.Address;
import com.example.delivery.service.Entities.User;
import com.example.delivery.service.Enum.Role;
import com.example.delivery.service.Exceptions.NotFoundException;
import com.example.delivery.service.Repository.AddressRepository;
import com.example.delivery.service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final Logger logger = LoggerFactory.getLogger(ProfileService.class);


    public ProfileInfoResponse getUserInformation(String username) {
        logger.info("Getting user information for user: " + username);
        ProfileInfoResponse profileInfoResponse = new ProfileInfoResponse();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found ProfileService"));
        profileInfoResponse.setUsername(user.getUsername());
        profileInfoResponse.setEmail(user.getEmail());
        profileInfoResponse.setRole(user.getRole());
        profileInfoResponse.setId(user.getId());
        profileInfoResponse.setPhone(user.getNumber());
        List<AddressResponse> addressResponses = new ArrayList<>();

        try {
            List<Address> addresses = addressRepository.findAddressByUser(user);
            System.out.println(addresses);
            if (addresses != null && !addresses.isEmpty()) {
                for (Address address : addresses) {
                    AddressResponse addressResponse = new AddressResponse();
                    addressResponse.setId(address.getId());
                    addressResponse.setStreet(address.getStreet());
                    addressResponse.setCity(address.getCity());
                    addressResponse.setHouseNumber(address.getHouseNumber());
                    addressResponse.setStreet(address.getApartment());
                    addressResponse.setPostalCode(address.getPostalCode());
                    addressResponses.add(addressResponse);
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching addresses: " + e.getMessage());
        }
        logger.info("User information for user: " + username + " fetched successfully");
        profileInfoResponse.setAddressList(addressResponses);
        return profileInfoResponse;
    }

    public void updateUserProfile(String username, UpdateProfileRequest updateProfileRequest) {
        User checkUser = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found, profileService"));
        if (updateProfileRequest.getEmail().isEmpty() && updateProfileRequest.getPhone().isEmpty()){
            logger.info("Requested update is empty for user: " + username);
            throw new IllegalArgumentException("Requested update is empty");
        }
        if (userRepository.existsByEmail(updateProfileRequest.getEmail()) && !updateProfileRequest.getEmail().equals(checkUser.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.existsByNumber(updateProfileRequest.getPhone()) && !updateProfileRequest.getPhone().equals(checkUser.getNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        logger.info("Updating user profile for user: " + username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setEmail(updateProfileRequest.getEmail());
        user.setNumber(updateProfileRequest.getPhone());
        userRepository.save(user);
        logger.info("User profile for user: " + username + " updated successfully");
    }
}

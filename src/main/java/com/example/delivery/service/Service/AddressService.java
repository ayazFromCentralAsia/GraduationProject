package com.example.delivery.service.Service;


import com.example.delivery.service.Dto.Address.AddressRequest;
import com.example.delivery.service.Dto.Address.AddressResponse;
import com.example.delivery.service.Entities.Address;
import com.example.delivery.service.Entities.User;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Exceptions.FailedDeleteException;
import com.example.delivery.service.Exceptions.NotFoundException;
import com.example.delivery.service.Repository.AddressRepository;
import com.example.delivery.service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    @Autowired
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(AddressService.class);

    public AddressResponse createAddress(AddressRequest address) {
        logger.info("Creating new address for user");
        Address newAddress;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            newAddress = new Address();
            newAddress.setHouseNumber(address.getHouseNumber());
            newAddress.setStreet(address.getStreet());
            newAddress.setCity(address.getCity());
            newAddress.setApartment(address.getApartment());
            newAddress.setPostalCode(address.getPostalCode());
            User userEntity = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new NotFoundException("User not found, AddressService"));
            newAddress.setUser(userEntity);
            addressRepository.save(newAddress);
        } catch (FailedCreateException e) {
            logger.error("Error creating new address for user: " + e.getMessage());
            throw e;
        }
        logger.info("New address created for user");
        return modelMapper.map(newAddress, AddressResponse.class);
    }
    public List<AddressResponse> getAllAddresses() {
        logger.info("Getting all addresses for user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Address> addressList = addressRepository.findAddressByUser(userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new NotFoundException("User not found, AddressService, getAllAddresses()")));
        List<AddressResponse> addressResponseList = new ArrayList<>();
        for (Address address : addressList) {
            addressResponseList.add(modelMapper.map(address, AddressResponse.class));
        }
        logger.info("All addresses retrieved for user");
        return addressResponseList;
    }
    public void deleteAddress(UUID id) {
        try {
            logger.info("Deleting address for user");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User userEntity = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                    new NotFoundException("User not found, AddressService, deleteAddress()"));
            Address address = addressRepository.findById(id).orElseThrow(() ->
                    new NotFoundException("Address not found, AddressService, deleteAddress()"));
            if (userEntity.getId().equals(address.getUser().getId())) {
                addressRepository.deleteById(id);
                logger.info("Address deleted for user");
            } else {
                logger.error("User not authorized to delete address");
            }
        }catch (FailedDeleteException e){
            logger.error("Error deleting address for user: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

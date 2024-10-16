package com.example.delivery.service.Service;

import com.example.delivery.service.Dto.Parcels.CreateParcelsRequest;
import com.example.delivery.service.Dto.Parcels.CreateParcelsResponse;
import com.example.delivery.service.Entities.Parcels;
import com.example.delivery.service.Enum.Status;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Exceptions.NotFoundException;
import com.example.delivery.service.Repository.ParcelsRepository;
import com.example.delivery.service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParcelsService {
    private final ParcelsRepository parcelsRepository;
    private final UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    private final Logger logger = LoggerFactory.getLogger(ParcelsService.class);
    public CreateParcelsResponse createParcel(CreateParcelsRequest request) {
        try {
            logger.info("Creating parcel with request: {}", request);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CreateParcelsResponse createParcelsResponse = new CreateParcelsResponse();

            if (request.getPickupAddressId() != null && request.getDeliveryAddressId() != null) {
                UUID uuidFrom = UUID.fromString(request.getPickupAddressId());
                UUID uuidTo = UUID.fromString(request.getDeliveryAddressId());

                Status status = Status.PENDING;

                LocalDate date = LocalDate.now();

                Parcels parcels = Parcels.builder()
                        .pickupAddressId(uuidFrom)
                        .deliveryAddressId(uuidTo)
                        .weight(request.getWeight())
                        .date(date)
                        .user(userRepository.findByUsername(authentication.getName())
                                .orElseThrow(() -> new NotFoundException("User not found ParcelsService, createParcel()")))
                        .build();
                parcels.setStatus(status);
//                parcelsRepository.save(parcels);
                modelMapper.map(parcels, createParcelsResponse);
                logger.info("Parcel created with response: {}", createParcelsResponse);
                return createParcelsResponse;
            } else {
                throw new IllegalArgumentException("Pickup and delivery address cannot be null");
            }
        } catch (FailedCreateException e) {
            logger.error("Error in creating parcel: {}", e.getMessage());
            throw e;
        }
    }
}

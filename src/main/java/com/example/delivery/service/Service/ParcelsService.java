package com.example.delivery.service.Service;

import com.example.delivery.service.Dto.Parcels.*;
import com.example.delivery.service.Entities.Couriers;
import com.example.delivery.service.Entities.Parcels;
import com.example.delivery.service.Enum.Status;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Exceptions.FailedGiveParcels;
import com.example.delivery.service.Exceptions.NotFoundException;
import com.example.delivery.service.Repository.CouriersRepository;
import com.example.delivery.service.Repository.ParcelsRepository;
import com.example.delivery.service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.delivery.service.Enum.Status.PENDING;

@Service
@RequiredArgsConstructor
public class ParcelsService {
    private final ParcelsRepository parcelsRepository;
    private final UserRepository userRepository;
    private final CouriersRepository couriersRepository;

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
                Date currentDate = new Date();
                Timestamp timestamp = new Timestamp(currentDate.getTime());
                System.out.println(timestamp);
                Parcels parcels = new Parcels(UUID.randomUUID(),PENDING,userRepository.
                        findByUsername(authentication.getName()).orElseThrow(() -> new NotFoundException("User not found ProfileService"))
                        ,null,timestamp,uuidFrom,uuidTo,request.getWeight());
                modelMapper.map(parcels, createParcelsResponse);
                parcelsRepository.save(parcels);
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

    public void cancelParcels(UUID id){
        logger.info("Canceling parcels with id: {}", id);
        Parcels parcels = parcelsRepository.findById(id).orElseThrow(() -> new NotFoundException("Parcel not found"));
        try {
            parcels.setStatus(Status.CANCELED);
            parcelsRepository.save(parcels);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid status");
        }
        logger.info("Parcels canceled successfully");
    }

    public ParcelsResponse getParcelsById(UUID uuid){
        try{
            logger.info("Getting parcels by id with uuid: {}", uuid);
            Parcels parcels = parcelsRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Parcel not found"));
            ParcelsResponse parcelsResponse = new ParcelsResponse();
            modelMapper.map(parcels, parcelsResponse);
            logger.info("Parcels found with response: {}", parcelsResponse);
            return parcelsResponse;
        }catch (FailedGiveParcels e){
            throw new FailedGiveParcels("Error in getting parcels by id: " + e.getMessage());
        }
    }

    public void updateParcelsStatus(UUID uuid, ParcelsStatusRequest status) {
        try {
            logger.info("Updating parcel status with request: {}", status);
            Parcels parcels = parcelsRepository.findById(uuid).orElseThrow(() -> new NotFoundException("Parcel not found"));
            if (status == null){
                throw new IllegalArgumentException("Status cannot be null");
            }
            try {
                Status statusChange = Status.valueOf(String.valueOf(status.getParcelStatus()));
                parcels.setStatus(statusChange);
                parcelsRepository.save(parcels);
            }catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Invalid status");
            }
            logger.info("Parcel status updated successfully");
        } catch (FailedCreateException e) {
            throw new FailedCreateException("Error in updating parcel status: " + e.getMessage());
        }
    }

    public void pickParcel(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Picking parcel with id: {}, courier: {}", id, authentication.getName());
        Parcels parcels = parcelsRepository.findById(id).orElseThrow(() -> new NotFoundException("Parcel not found"));
        try {
            if (parcels.getStatus() == Status.PENDING) {
                parcels.setStatus(Status.IN_TRANSIT);
                parcels.setCourierId(couriersRepository.findByUsername(authentication.getName()).orElseThrow(() -> new NotFoundException("Courier not found ParcelsService")));
                parcelsRepository.save(parcels);
            } else {
                throw new IllegalArgumentException("Parcel is not in pending status");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Error in picking parcel: {}", e.getMessage());
            throw e;
        }
    }

    public List<ParcelsCourierResponse> getAllParcels() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Couriers couriers = couriersRepository.findByUsername(authentication.getName()).orElseGet(() -> null);
            logger.info("Getting all parcels");
            List<Parcels> parcelsList = parcelsRepository.findAll();
            List<ParcelsCourierResponse> parcelsCourierResponseList = new ArrayList<>();
            for (Parcels parcels : parcelsList) {
                if (couriers != null) {
                    if (parcels.getStatus() == PENDING) {
                        ParcelsCourierResponse parcelsCourierResponse = new ParcelsCourierResponse();
                        modelMapper.map(parcels, parcelsCourierResponse);
                        parcelsCourierResponseList.add(parcelsCourierResponse);
                    }
                }else {
                    ParcelsCourierResponse parcelsCourierResponse = new ParcelsCourierResponse();
                    modelMapper.map(parcels, parcelsCourierResponse);
                    parcelsCourierResponseList.add(parcelsCourierResponse);
                }
            }
            logger.info("Parcels found with response: {}", parcelsCourierResponseList);
            return parcelsCourierResponseList;
        } catch (FailedGiveParcels e) {
            throw new FailedGiveParcels("Error in getting all parcels: " + e.getMessage());
        }
    }
}

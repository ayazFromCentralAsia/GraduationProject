package com.example.delivery.service.Dto.Parcels;



import com.example.delivery.service.Enum.Status;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
public class ParcelsResponse {
    UUID id;
    UUID userId;
    UUID pickupAddressId;
    UUID deliveryAddressId;
    Status status;
    Date date;
    Double weight;
    UUID courierId;
}
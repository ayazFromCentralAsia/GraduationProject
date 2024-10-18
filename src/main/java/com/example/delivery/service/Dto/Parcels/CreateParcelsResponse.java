package com.example.delivery.service.Dto.Parcels;


import com.example.delivery.service.Enum.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class CreateParcelsResponse {
    private UUID id;
    private UUID userId;
    private UUID pickupAddressId;
    private UUID deliveryAddressId;
    private Status status;
    private Date date;
    private double weight;
}



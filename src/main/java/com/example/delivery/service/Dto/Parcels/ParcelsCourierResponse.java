package com.example.delivery.service.Dto.Parcels;

import com.example.delivery.service.Enum.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ParcelsCourierResponse {
    private UUID id;
    private UUID userId;
    private UUID pickupAddressId;
    private UUID deliveryAddressId;
    private Status status;
    private Date date;
    private double weight;
}

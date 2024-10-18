package com.example.delivery.service.Dto.Parcels;

import com.example.delivery.service.Enum.Status;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ParcelsStatusRequest {
    @Pattern(regexp = "IN_TRANSIT|PENDING|DELIVERED|CANCELLED", message = "Status must be one of IN_TRANSIT, PENDING, DELIVERED, CANCELLED")
    Status parcelStatus;
}

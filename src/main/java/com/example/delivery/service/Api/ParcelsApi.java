package com.example.delivery.service.Api;

import com.example.delivery.service.Dto.Parcels.CreateParcelsRequest;
import com.example.delivery.service.Dto.Parcels.CreateParcelsResponse;
import com.example.delivery.service.Service.ParcelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/parcels")
@RequiredArgsConstructor
@Tag(name = "Parcels API", description = "API for managing Parcels Information")
public class ParcelsApi {

    private final ParcelsService parcelsService;


    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    @Operation(summary = "Create Parcels")
    public CreateParcelsResponse createParcels(@RequestBody CreateParcelsRequest request){
        return parcelsService.createParcel(request);
    }
}

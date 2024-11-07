package com.example.delivery.service.Api;

import com.example.delivery.service.Dto.Parcels.*;
import com.example.delivery.service.Exceptions.FailedCreateException;
import com.example.delivery.service.Service.ParcelsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','COURIER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get Parcel By ID")
    public ParcelsResponse getParcelsById(@PathVariable("id") UUID id){
        return parcelsService.getParcelsById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','COURIER')")
    @PutMapping("/{parcelId}/status")
    @Operation(summary = "Update Parcel By ID")
    public ResponseEntity<Map<String, String>> updateParcelsById(@PathVariable("parcelId") UUID id, @RequestBody ParcelsStatusRequest request){
        try {
            parcelsService.updateParcelsStatus(id, request);
            return ResponseEntity.ok(Map.of("message", "Parcel status updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (FailedCreateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to update parcel status: "));
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}/cancel")
    @Operation(summary = "Cancel Parcel By Id, With User")
    public ResponseEntity<?> cancelParcels(@PathVariable("id") UUID id){
        try {
            parcelsService.cancelParcels(id);
            return ResponseEntity.ok(Map.of("message", "Parcel status updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','COURIER')")
    @GetMapping("/getAll")
    @Operation(summary = "Get all Parcels")
    public List<ParcelsCourierResponse> getAllParcels(){
        return parcelsService.getAllParcels();
    }

    @PreAuthorize("hasAnyAuthority('COURIER')")
    @PutMapping("/{id}/pick")
    @Operation(summary = "Pick parcel")
    public ResponseEntity<?> pickParcel(@PathVariable("id") UUID id){
        try {
            parcelsService.pickParcel(id);
            return ResponseEntity.ok(Map.of("message", "Parcel was Picked"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Parcel has't courier   " +e.getMessage()));
        }
    }
}

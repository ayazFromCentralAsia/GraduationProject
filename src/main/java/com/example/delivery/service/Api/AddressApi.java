package com.example.delivery.service.Api;

import com.example.delivery.service.Dto.Address.AddressRequest;
import com.example.delivery.service.Dto.Address.AddressResponse;
import com.example.delivery.service.Service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Tag(name = "Address API", description = "API for Address Information")
public class AddressApi {

    private final AddressService addressService;

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PostMapping
    @Operation(summary = "Create Address")
    public AddressResponse createAddress(@RequestBody AddressRequest address) {
        return addressService.createAddress(address);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get All Address")
    public List<AddressResponse> getAllAddressByUser() {
        return addressService.getAllAddresses();
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Address")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") UUID id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.ok().body(Map.of("message", "Address deleted successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", "Address not found"));
        }
    }
}

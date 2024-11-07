package com.example.delivery.service.Api;


import com.example.delivery.service.Dto.Couriers.CouriersDto;
import com.example.delivery.service.Dto.User.UserInfoResponse;
import com.example.delivery.service.Service.AdminService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Admin API")
public class AdminApi {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/users")
    @Operation(summary = "Get All User")
    public List<UserInfoResponse> getProfileInfo() {
        return adminService.getAllUser();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/couriers")
    @Operation(summary = "Get All Couriers")
    public List<CouriersDto> getAllCouriers() {
        return adminService.getAllCouriers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/couriers")
    @Operation(summary = "Create Courier")
    public ResponseEntity<?> createCourier(@RequestBody CouriersDto couriersDto) {
        return adminService.createCourier(couriersDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/users/{id}")
    @Operation(summary = "Delete User")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        try {
            adminService.deleteUser(id);
            return ResponseEntity.ok(Map.of("message", "User blocked successfully"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/couriers/{id}")
    @Operation(summary = "Delete Courier, Only Couriers ID Not User")
    public ResponseEntity<?> deleteCouriers(@PathVariable UUID id) {
        try {
            adminService.deleteCourier(id);
            return ResponseEntity.ok(Map.of("message", "Courier blocked successfully"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/couriers/{id}")
    @Operation(summary = "Change Courier Info")
    public ResponseEntity<?> changeCourierInfo(@PathVariable UUID id, @RequestBody CouriersDto couriersDto) {
        try {
            adminService.changeCourierInfo(id, couriersDto);
            return ResponseEntity.ok(Map.of("message", "Courier Changed successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

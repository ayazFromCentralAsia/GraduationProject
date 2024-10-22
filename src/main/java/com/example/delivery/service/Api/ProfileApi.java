package com.example.delivery.service.Api;

import com.example.delivery.service.Dto.User.ProfileInfoResponse;
import com.example.delivery.service.Dto.User.UpdateProfileRequest;
import com.example.delivery.service.Service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Information")
public class ProfileApi {

    private final ProfileService profileService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','COURIER')")
    @GetMapping("/me")
    @Operation(summary = "Get user Information")
    public ProfileInfoResponse aboutMe(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return profileService.getUserInformation(username);
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','COURIER')")
    @PutMapping("/me")
    @Operation(summary = "Change user Information")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest updateProfileRequest, Principal principal) {
        try{
            profileService.updateUserProfile(principal.getName(), updateProfileRequest);
            return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}

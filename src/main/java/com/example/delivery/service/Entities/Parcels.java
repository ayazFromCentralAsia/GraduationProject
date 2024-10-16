package com.example.delivery.service.Entities;

import com.example.delivery.service.Enum.Status;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parcels")
@Entity
public class Parcels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "courier_id")
    private Couriers courierId;

    @NotBlank
    private LocalDate date;
    private UUID pickupAddressId;
    private UUID deliveryAddressId;

    private Double weight;

}

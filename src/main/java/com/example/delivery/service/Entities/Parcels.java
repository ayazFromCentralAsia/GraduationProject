package com.example.delivery.service.Entities;

import com.example.delivery.service.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcels")
@Entity
public class Parcels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "parcel_status")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "courier_id")
    private Couriers courierId;

    @Column(name = "creation_date")
    private Date date;

    private UUID pickupAddressId;
    private UUID deliveryAddressId;

    private Double weight;

    @Override
    public String toString() {
        return "Parcels{" +
                "id=" + id +
                ", status=" + status +
                ", user=" + user +
                ", courierId=" + courierId +
                ", date=" + date +
                ", pickupAddressId=" + pickupAddressId +
                ", deliveryAddressId=" + deliveryAddressId +
                ", weight=" + weight +
                '}';
    }
}

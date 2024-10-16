package com.example.delivery.service.Repository;

import com.example.delivery.service.Entities.Parcels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ParcelsRepository extends JpaRepository<Parcels, UUID> {
}

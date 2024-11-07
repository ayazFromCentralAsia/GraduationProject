package com.example.delivery.service.Repository;

import com.example.delivery.service.Entities.Couriers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CouriersRepository extends JpaRepository<Couriers, UUID> {
    Optional<Couriers> findByUsername(String username);

    Boolean existsByEmail(String username);
    Boolean existsByPhone(String phone);
    Boolean existsByUsername(String username);
}

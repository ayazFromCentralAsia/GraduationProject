package com.example.delivery.service.Repository;


import com.example.delivery.service.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername (String username);
    Optional<User> findByEmail (String email);
    Optional<User> findByPassword (String password);
    Optional<User> findByNumber (String number);
    Boolean existsByUsername (String username);
    Boolean existsByEmail (String email);
    Boolean existsByNumber (String number);
}

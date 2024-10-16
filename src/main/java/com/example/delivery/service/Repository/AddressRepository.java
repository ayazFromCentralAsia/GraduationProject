package com.example.delivery.service.Repository;

import com.example.delivery.service.Dto.Address.AddressResponse;
import com.example.delivery.service.Entities.Address;
import com.example.delivery.service.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findAddressByUser(User user);
}

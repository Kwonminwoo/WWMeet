package com.example.wwmeet_backend.address.repository;

import com.example.wwmeet_backend.address.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}

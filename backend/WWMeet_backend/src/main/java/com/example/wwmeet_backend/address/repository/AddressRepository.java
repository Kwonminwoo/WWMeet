package com.example.wwmeet_backend.address.repository;

import com.example.wwmeet_backend.address.domain.Address;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByParticipantId(Long id);
}

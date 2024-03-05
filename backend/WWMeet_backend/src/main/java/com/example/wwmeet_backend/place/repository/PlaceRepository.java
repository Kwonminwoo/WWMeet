package com.example.wwmeet_backend.place.repository;

import com.example.wwmeet_backend.place.domain.Place;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByParticipantId(Long id);
}

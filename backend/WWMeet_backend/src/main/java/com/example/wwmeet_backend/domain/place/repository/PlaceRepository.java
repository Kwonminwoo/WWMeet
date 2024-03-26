package com.example.wwmeet_backend.domain.place.repository;

import com.example.wwmeet_backend.domain.place.entity.Place;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    Optional<Place> findByParticipantId(Long id);
}

package com.example.wwmeet_backend.domain.place.service;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.domain.participant.entity.Participant;
import com.example.wwmeet_backend.domain.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.domain.place.entity.Place;
import com.example.wwmeet_backend.domain.place.dto.request.SavePlaceRequest;
import com.example.wwmeet_backend.domain.place.dto.response.FindAllPlaceResponse;
import com.example.wwmeet_backend.domain.place.repository.PlaceRepository;
import com.example.wwmeet_backend.global.exception.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final ParticipantRepository participantRepository;
    private final AppointmentRepository appointmentRepository;

    public void saveAddress(SavePlaceRequest request) {
        Appointment targetAppointment = appointmentRepository.findById(request.getAppointmentId())
            .orElseThrow(DataNotFoundException::new);

        Participant participant = participantRepository.findByParticipantName(targetAppointment,
                request.getParticipantName()).orElseThrow(DataNotFoundException::new);

        Place place = Place.builder()
            .participant(participant)
            .address(request.getAddress())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .build();

        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public List<FindAllPlaceResponse> findAllAddress(Long appointmentId) {
        Appointment targetAppointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(DataNotFoundException::new);

        List<Participant> participantList = targetAppointment.getParticipantList();

        List<FindAllPlaceResponse> response = new ArrayList<>();
        for (Participant participant : participantList) {
            Place place = placeRepository.findByParticipantId(participant.getId())
                .orElseThrow(DataNotFoundException::new);

            response.add(
                new FindAllPlaceResponse(participant.getParticipantName(), place.getAddress()
                    , place.getLatitude(), place.getLongitude()));
        }

        return response;
    }
}

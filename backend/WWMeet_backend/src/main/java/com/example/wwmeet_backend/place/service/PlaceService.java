package com.example.wwmeet_backend.place.service;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import com.example.wwmeet_backend.place.domain.Place;
import com.example.wwmeet_backend.place.dto.request.SavePlaceRequest;
import com.example.wwmeet_backend.place.dto.response.FindAllPlaceResponse;
import com.example.wwmeet_backend.place.repository.PlaceRepository;
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

    public void saveAddress(SavePlaceRequest request){
        Appointment targetAppointment = appointmentRepository.findById(request.getAppointmentId())
            .orElseThrow(RuntimeException::new);
        Participant participant = participantRepository.findByParticipantName(targetAppointment,
                request.getParticipantName())
            .orElseThrow(RuntimeException::new);

        Place place = Place.builder()
            .participant(participant)
            .address(request.getAddress())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .build();

        placeRepository.save(place);
    }

    @Transactional(readOnly = true)
    public List<FindAllPlaceResponse> findAllAddress(Long appointmentId){
        Appointment targetAppointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(RuntimeException::new);

        List<Participant> participantList = targetAppointment.getParticipantList();

        List<FindAllPlaceResponse> response = new ArrayList<>();
        for (Participant participant : participantList) {
            Place place = placeRepository.findByParticipantId(participant.getId())
                .orElseThrow(RuntimeException::new);

            response.add(new FindAllPlaceResponse(participant.getParticipantName(), place.getAddress()
                , place.getLatitude(), place.getLongitude()));
        }
        
        return response;
    }
}

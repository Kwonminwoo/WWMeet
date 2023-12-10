package com.example.wwmeet_backend.address.service;

import com.example.wwmeet_backend.address.domain.Address;
import com.example.wwmeet_backend.address.dto.request.SaveAddressRequest;
import com.example.wwmeet_backend.address.repository.AddressRepository;
import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ParticipantRepository participantRepository;
    private final AppointmentRepository appointmentRepository;

    public void saveAddress(SaveAddressRequest request){
        Appointment targetAppointment = appointmentRepository.findById(request.getAppointmentId())
            .orElseThrow(RuntimeException::new);
        Participant participant = participantRepository.findByParticipantName(targetAppointment,
                request.getParticipantName())
            .orElseThrow(RuntimeException::new);

        Address address = Address.builder()
            .participant(participant)
            .address(request.getAddress())
            .latitude(request.getLatitude())
            .longitude(request.getLongitude())
            .build();

        addressRepository.save(address);
    }
}

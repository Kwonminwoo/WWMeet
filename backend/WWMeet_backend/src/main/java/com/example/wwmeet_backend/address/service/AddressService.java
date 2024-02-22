package com.example.wwmeet_backend.address.service;

import com.example.wwmeet_backend.address.domain.Address;
import com.example.wwmeet_backend.address.dto.request.SaveAddressRequest;
import com.example.wwmeet_backend.address.dto.response.FindAllAddressResponse;
import com.example.wwmeet_backend.address.repository.AddressRepository;
import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointment.repository.AppointmentRepository;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    @Transactional(readOnly = true)
    public List<FindAllAddressResponse> findAllAddress(Long appointmentId){
        Appointment targetAppointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(RuntimeException::new);

        List<Participant> participantList = targetAppointment.getParticipantList();

        List<FindAllAddressResponse> response = new ArrayList<>();
        for (Participant participant : participantList) {
            Address address = addressRepository.findByParticipantId(participant.getId())
                .orElseThrow(RuntimeException::new);

            response.add(new FindAllAddressResponse(participant.getParticipantName(), address.getAddress()
                , address.getLatitude(), address.getLongitude()));
        }
        
        return response;
    }
}

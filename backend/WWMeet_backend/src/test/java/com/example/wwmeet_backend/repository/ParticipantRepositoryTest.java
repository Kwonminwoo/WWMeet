package com.example.wwmeet_backend.repository;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.participant.domain.Participant;
import com.example.wwmeet_backend.participant.repository.ParticipantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
class ParticipantRepositoryTest {
    @Autowired
    ParticipantRepository participantRepository;
    @Test
    void saveParticipant(){
        Participant participant = new Participant(null, new Appointment(1L, "test", "test", "test", 2, null), "me");

        Participant savedParticipant = participantRepository.save(participant);

        Assertions.assertThat(participant.getParticipantName()).isEqualTo(savedParticipant.getParticipantName());
    }
}
package com.example.wwmeet_backend.repository;

import com.example.wwmeet_backend.domain.Appointment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 디비를 선택하는 것
// NONE는 실제 resource에 있는 Database를 이용하는 것.
// Any는 무조건 변경한다. 이외에 다른것들도 있음.
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties") //test용 properties 파일 설정
@DataJpaTest
class AppointmentRepositoryTest {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Test
    void findAllAppointment(){
        Appointment appointment = new Appointment(1L, "test name", "두정", "find1", 2, null, null);

        String findCode = "find1";

        Appointment savedAppointment = appointmentRepository.save(appointment);
        List<Appointment> findAppointmentList = appointmentRepository.findByAppointmentCode(findCode);

        Assertions.assertThat(findAppointmentList.size()).isEqualTo(1);
        Assertions.assertThat(findAppointmentList.get(0)).isSameAs(savedAppointment);
    }
}
package com.example.wwmeet_backend.domain.appointmentDate.service;

import com.example.wwmeet_backend.domain.appointment.entity.Appointment;
import com.example.wwmeet_backend.domain.appointmentDate.entity.AppointmentDate;
import com.example.wwmeet_backend.domain.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.domain.possibleschedule.entity.PossibleSchedule;
import com.example.wwmeet_backend.domain.possibleschedule.repository.PossibleScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentDateService {

    private final AppointmentDateRepository appointmentDateRepository;
    private final PossibleScheduleRepository possibleScheduleRepository;

    public void setAppointmentDate(Appointment appointment) {
        Map<LocalDateTime, Integer> localDateTimeMap = new HashMap<>();
        List<PossibleSchedule> possibleScheduleList = possibleScheduleRepository.findAllByAppointmentId(
            appointment.getId());

        possibleScheduleList.sort(new Comparator<PossibleSchedule>() {
            @Override
            public int compare(PossibleSchedule o1, PossibleSchedule o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });

        // 키 값으로 다 쪼개서 넣고 cnt 셈
        for (PossibleSchedule possibleSchedule : possibleScheduleList) {
            LocalDateTime datetimeKey = possibleSchedule.getStartTime();
            int cnt = 1;
            while (!datetimeKey.isAfter(possibleSchedule.getEndTime().minusHours(1))) {
                if (localDateTimeMap.containsKey(datetimeKey)) {
                    cnt = localDateTimeMap.get(datetimeKey) + 1;
                }
                localDateTimeMap.put(datetimeKey, cnt);

                if (datetimeKey.getDayOfMonth() == datetimeKey.toLocalDate().lengthOfMonth()) {
                    datetimeKey.plusMonths(1);
                }
                if (datetimeKey.getHour() == 23) {
                    datetimeKey.plusDays(1);
                }
                datetimeKey = datetimeKey.plusHours(1);
            }
        }

        List<LocalDateTime> dateTimeMapKeySet = new ArrayList<>(localDateTimeMap.keySet());
        Collections.sort(dateTimeMapKeySet);

        int preCnt = 1;
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime preDate = LocalDateTime.MIN;
        Map<List<LocalDateTime>, Integer> allDateTimeMergedMap = new HashMap<>();
        int i = 0;
        for (LocalDateTime dateTime : dateTimeMapKeySet) {
            if (i == 0) {
                startDate = dateTime;
            }
            int cnt = localDateTimeMap.get(dateTime);
            if (cnt == preCnt && preDate.plusHours(1).isEqual(dateTime) || i == 0) {
                preDate = dateTime;
                preCnt = cnt;
            } else {
                allDateTimeMergedMap.put(List.of(startDate, preDate.plusHours(1)), preCnt);
                startDate = dateTime;
                preDate = startDate;
                preCnt = cnt;
            }
            i++;
        }
        allDateTimeMergedMap.put(List.of(startDate, preDate.plusHours(1)), preCnt);

        List<List<LocalDateTime>> mergedMapKeySet = new ArrayList<>(allDateTimeMergedMap.keySet());
        mergedMapKeySet.sort(new Comparator<List<LocalDateTime>>() {
            @Override
            public int compare(List<LocalDateTime> o1, List<LocalDateTime> o2) {
                return allDateTimeMergedMap.get(o2).compareTo(allDateTimeMergedMap.get(o1));
            }
        }.thenComparing(new Comparator<List<LocalDateTime>>() {
            @Override
            public int compare(List<LocalDateTime> o1, List<LocalDateTime> o2) {
                return o1.get(0).compareTo(o2.get(0));
            }
        }));

        int rank = 1;
        for (List<LocalDateTime> key : mergedMapKeySet) {
            if (rank++ > 3) {
                break;
            }
            AppointmentDate appointmentDate = AppointmentDate.builder()
                .startDate(key.get(0))
                .endDate(key.get(1))
                .appointment(appointment)
                .build();

            appointmentDateRepository.save(appointmentDate);
        }
    }
}

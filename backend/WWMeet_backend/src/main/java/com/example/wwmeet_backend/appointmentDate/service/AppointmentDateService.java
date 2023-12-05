package com.example.wwmeet_backend.appointmentDate.service;

import com.example.wwmeet_backend.appointment.domain.Appointment;
import com.example.wwmeet_backend.appointmentDate.domain.AppointmentDate;
import com.example.wwmeet_backend.appointmentDate.repository.AppointmentDateRepository;
import com.example.wwmeet_backend.possibleschedule.domain.PossibleSchedule;
import com.example.wwmeet_backend.possibleschedule.repository.PossibleScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentDateService {
    private AppointmentDateRepository appointmentDateRepository;
    private PossibleScheduleRepository possibleScheduleRepository;

    public void setAppointmentDate(Appointment appointment){
        Map<LocalDateTime, Integer> localDateTimeMap = new HashMap<>();
        List<PossibleSchedule> possibleScheduleList = possibleScheduleRepository.findAllByAppointmentId(
            appointment.getId());

        // 키 값으로 다 쪼개서 넣고 cnt 셈
        for (PossibleSchedule possibleSchedule : possibleScheduleList) {
            LocalDateTime datetimeKey = possibleSchedule.getStartTime();
            while (datetimeKey.isBefore(possibleSchedule.getEndTime())) {
                int cnt = 0;
                if(localDateTimeMap.containsKey(datetimeKey)){
                    cnt = localDateTimeMap.get(datetimeKey);
                }
                localDateTimeMap.put(datetimeKey, cnt++);
                datetimeKey = datetimeKey.plusDays(1);
            }
        }


        int preCnt = 0;
        LocalDateTime startDate = null;
        LocalDateTime preDate = null;
        Map<List<LocalDateTime>, Integer> allDateTimeMergedMap = new HashMap<>();
        for(LocalDateTime dateTime : localDateTimeMap.keySet()){
            int cnt = localDateTimeMap.get(dateTime);
            if(cnt == preCnt){
                preDate = dateTime;
            }else{
                allDateTimeMergedMap.put(List.of(startDate, preDate), preCnt);
                startDate = dateTime;
                preDate = startDate;
                preCnt = cnt;
            }
        }

        List<Entry<List<LocalDateTime>, Integer>> entries = new ArrayList<>(allDateTimeMergedMap.entrySet());
        entries.sort(new Comparator<Entry<List<LocalDateTime>, Integer>>() {
            @Override
            public int compare(Entry<List<LocalDateTime>, Integer> o1,
                Entry<List<LocalDateTime>, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Object[] mapKey = allDateTimeMergedMap.keySet().toArray();
        Arrays.sort(mapKey);

        int i = 0;
        for (List<LocalDateTime> key : allDateTimeMergedMap.keySet()) {
            if (i++ > 2){
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

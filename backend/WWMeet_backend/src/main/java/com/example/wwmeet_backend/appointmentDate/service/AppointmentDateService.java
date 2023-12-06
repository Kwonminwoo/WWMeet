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
    private final AppointmentDateRepository appointmentDateRepository;
    private final PossibleScheduleRepository possibleScheduleRepository;

    public void setAppointmentDate(Appointment appointment){
        Map<LocalDateTime, Integer> localDateTimeMap = new HashMap<>();
        List<PossibleSchedule> possibleScheduleList = possibleScheduleRepository.findAllByAppointmentId(
            appointment.getId());

        // 키 값으로 다 쪼개서 넣고 cnt 셈
        for (PossibleSchedule possibleSchedule : possibleScheduleList) {
            LocalDateTime datetimeKey = possibleSchedule.getStartTime();
            int cnt = 1;
            while (!datetimeKey.isAfter(possibleSchedule.getEndTime().minusHours(1))) {
                if(localDateTimeMap.containsKey(datetimeKey)){
                    cnt = localDateTimeMap.get(datetimeKey);
                }
                localDateTimeMap.put(datetimeKey, cnt++);

                if(datetimeKey.getDayOfMonth() == datetimeKey.toLocalDate().lengthOfMonth()){
                    datetimeKey.plusMonths(1);
                }
                if(datetimeKey.getHour() == 23){
                    datetimeKey.plusDays(1);
                }
                datetimeKey = datetimeKey.plusHours(1);
            }
        }


        int preCnt = 1;
        LocalDateTime startDate = LocalDateTime.MIN;
        LocalDateTime preDate = LocalDateTime.MIN;
        Map<List<LocalDateTime>, Integer> allDateTimeMergedMap = new HashMap<>();
        int i = 0;
        for(LocalDateTime dateTime : localDateTimeMap.keySet()){
            if(i == 0){
                startDate = dateTime;
            }
            int cnt = localDateTimeMap.get(dateTime);
            if(cnt == preCnt && preDate.plusHours(1).isEqual(dateTime) || i == 0){
                preDate = dateTime;
                preCnt = cnt;
            }else{
                allDateTimeMergedMap.put(List.of(startDate, preDate.plusHours(1)), preCnt);
                startDate = dateTime;
                preDate = startDate;
                preCnt = cnt;
            }
            i++;
        }
        allDateTimeMergedMap.put(List.of(startDate, preDate.plusHours(1)), preCnt);


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

        int rank = 1;
        for (List<LocalDateTime> key : allDateTimeMergedMap.keySet()) {
            if (rank++ > 3){
                break;
            }
            AppointmentDate appointmentDate = AppointmentDate.builder()
                .startDate(key.get(0))
                .endDate(key.get(1))
                .appointment(appointment)
                .build();

            System.out.println(key.get(0));
            System.out.println(key.get(1));

            appointmentDateRepository.save(appointmentDate);
        }
    }
}

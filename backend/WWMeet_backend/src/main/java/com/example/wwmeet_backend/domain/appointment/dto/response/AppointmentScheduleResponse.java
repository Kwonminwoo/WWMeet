package com.example.wwmeet_backend.domain.appointment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentScheduleResponse {

    private DateRangeResponse firstSchedule;
    private DateRangeResponse secondSchedule;
    private DateRangeResponse thirdSchedule;

    @Builder
    public AppointmentScheduleResponse(DateRangeResponse firstSchedule,
        DateRangeResponse secondSchedule, DateRangeResponse thirdSchedule) {
        this.firstSchedule = firstSchedule;
        this.secondSchedule = secondSchedule;
        this.thirdSchedule = thirdSchedule;
    }

}

package com.example.wwmeet_android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.domain.Appointment;
import com.example.wwmeet_android.dto.FindAppointmentListResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.ViewHolder> {
    List<FindAppointmentListResponse> appointmentList = new ArrayList<>();
    Context context;
    OnItemClickEventListener listener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dateTimeTextView;
        private TextView progressTextView;
        private ImageView progressImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTimeTextView = itemView.findViewById(R.id.item_appointment_list_datetime_text);
            progressTextView = itemView.findViewById(R.id.item_appointment_list_progress_text);
            progressImageView = itemView.findViewById(R.id.item_appointment_list_progress_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        listener.onItemClick(v, pos);
                    }
                }
            });
        }
    }

    public AppointmentListAdapter(List<FindAppointmentListResponse> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public AppointmentListAdapter() {
    }

    public void setList(List<FindAppointmentListResponse> list) {
        appointmentList = list;
    }

    @NonNull
    @Override
    public AppointmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_list, parent, false);
        AppointmentListAdapter.ViewHolder viewHolder = new AppointmentListAdapter.ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentListAdapter.ViewHolder holder, int position) {
        FindAppointmentListResponse appointment = appointmentList.get(position);
        String progressText = "";
        int circleId = 0;
        String dateTimeText = "";
        if(appointment.isFinishVote()){
            // 투표가 끝났다면
            progressText = "투표 진행 완료";
            circleId = R.drawable.redcircle;
            dateTimeText = "기한: " + appointment.getDeadline();
        }else{
            progressText = "투표 진행 중";
            circleId = R.drawable.greencircle;
            dateTimeText = "약속 일: " + appointment.getDeadline();
        }


        holder.dateTimeTextView.setText(dateTimeText);
        //String formattedLimitTime = appointment.getDeadline().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
        holder.progressTextView.setText(progressText);
        holder.progressImageView.setImageDrawable(ContextCompat.getDrawable(context, circleId));
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public interface OnItemClickEventListener{
        void onItemClick(View view, int position);
    }

    public void setItemClickListener(OnItemClickEventListener listener) {
        this.listener = listener;
    }
}

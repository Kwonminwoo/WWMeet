package com.example.wwmeet_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.domain.Appointment;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AppointmentBeforeAdapter extends RecyclerView.Adapter<AppointmentBeforeAdapter.ViewHolder> {
    List<Appointment> appointmentList = new ArrayList<>();
    Context context;
    OnItemClickEventListener listener;
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView limitTimeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_appointment_list_deadline_text);
            limitTimeTextView = itemView.findViewById(R.id.item_appointment_list_progress_text);

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

    public AppointmentBeforeAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public AppointmentBeforeAdapter() {
    }

    public void setList(List<Appointment> list) {
        appointmentList = list;
    }

    @NonNull
    @Override
    public AppointmentBeforeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_list, parent, false);
        AppointmentBeforeAdapter.ViewHolder viewHolder = new AppointmentBeforeAdapter.ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentBeforeAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);

        holder.nameTextView.setText(appointment.getName());
        String formattedLimitTime = appointment.getLimitDate().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
        holder.limitTimeTextView.setText(formattedLimitTime);
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

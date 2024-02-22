package com.example.wwmeet_android.appointment.info;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantListAdapter extends RecyclerView.Adapter<ParticipantListAdapter.ViewHolder> {
    private List<Participant> participantList = new ArrayList<>();
    private int participantNum;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView participantName;
        private ImageView voteStateCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            participantName = itemView.findViewById(R.id.participant_item_name);
            voteStateCircle = itemView.findViewById(R.id.participant_item_circle);
        }
    }

    public void setList(List<Participant> list, int participantNum) {
        participantList = list;
        this.participantNum = participantNum;
        int listSize = participantList.size();
        for (int i = 0; i < (participantNum - listSize);i++) {
            participantList.add(new Participant("입장 전", false));
        }
    }

    public void setList(List<Participant> list){
        participantList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_participant_list, parent, false);
        ParticipantListAdapter.ViewHolder viewHolder = new ParticipantListAdapter.ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Participant participant = participantList.get(position);

        holder.participantName.setText(participant.getName());

        int circleId = 0;
        if (participant.isFinishVote()) {
            circleId = R.drawable.greencircle;
        }else{
            circleId = R.drawable.redcircle;
        }
        holder.voteStateCircle.setImageDrawable(ContextCompat.getDrawable(context, circleId));
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }


}

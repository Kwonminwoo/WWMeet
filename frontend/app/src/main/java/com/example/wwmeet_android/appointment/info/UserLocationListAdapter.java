package com.example.wwmeet_android.appointment.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.UserLocation;

import java.util.ArrayList;
import java.util.List;

public class UserLocationListAdapter extends RecyclerView.Adapter<UserLocationListAdapter.ViewHolder> {
    private List<UserLocation> UserLocationList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView UserLocationName;
        private TextView UserLocationAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserLocationName = itemView.findViewById(R.id.user_location_item_name);
            UserLocationAddress = itemView.findViewById(R.id.user_location_item_location);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_location_list, parent, false);
        UserLocationListAdapter.ViewHolder viewHolder = new UserLocationListAdapter.ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserLocationListAdapter.ViewHolder holder, int position) {
        UserLocation UserLocation = UserLocationList.get(position);

        holder.UserLocationName.setText(UserLocation.getName());
        holder.UserLocationAddress.setText(UserLocation.getAddress());
    }


    @Override
    public int getItemCount() {
        return UserLocationList.size();
    }

}

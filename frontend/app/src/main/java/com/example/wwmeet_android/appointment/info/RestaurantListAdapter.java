package com.example.wwmeet_android.appointment.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Restaurant;


import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private List<Restaurant> restaurantList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantNameText;
        private TextView menuText;
        private TextView addressText;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameText = itemView.findViewById(R.id.item_restaurant_name);
            menuText = itemView.findViewById(R.id.item_restaurant_menu);
            addressText = itemView.findViewById(R.id.item_restaurant_address);
            imageView = itemView.findViewById(R.id.item_restaurant_image);
        }
    }

    public void setRestaurantList(List<Restaurant> list){
        this.restaurantList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_list, parent, false);
        RestaurantListAdapter.ViewHolder viewHolder = new RestaurantListAdapter.ViewHolder(view);
        context = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);

        holder.restaurantNameText.setText(restaurant.getRestaurantName());
        holder.menuText.setText(restaurant.getMenu());
        holder.addressText.setText(restaurant.getAddress());
        // 이미지 어떻게 가져와지는지 확인 후
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

}

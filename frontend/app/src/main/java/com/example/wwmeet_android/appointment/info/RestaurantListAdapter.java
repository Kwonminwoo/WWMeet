package com.example.wwmeet_android.appointment.info;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Restaurant;


import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Restaurant> restaurantList = new ArrayList<>();
    private Context context;

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView restaurantNameText;
        private TextView menuText;
        private TextView addressText;
        private ImageView imageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantNameText = itemView.findViewById(R.id.item_restaurant_name);
            menuText = itemView.findViewById(R.id.item_restaurant_menu);
            addressText = itemView.findViewById(R.id.item_restaurant_address);
            imageView = itemView.findViewById(R.id.item_restaurant_image);
        }
    }

    public void addRestaurant(Restaurant restaurant){
        restaurantList.add(restaurant);
    }

    public void removeRestaurant(int position){
        restaurantList.remove(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int index = position;
        Restaurant restaurant = restaurantList.get(position);
        ((ItemViewHolder) holder).restaurantNameText.setText(restaurant.getRestaurantName());
        ((ItemViewHolder) holder).restaurantNameText.setTextColor(Color.BLUE);
        ((ItemViewHolder) holder).restaurantNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurantList.get(index).getUrl()));
                context.startActivity(webIntent);
            }
        });
        ((ItemViewHolder) holder).menuText.setText(restaurant.getMenu());
        ((ItemViewHolder) holder).addressText.setText(restaurant.getAddress());
        Glide.with(context)
                .load(restaurant.getImageUrl())
                .into(((ItemViewHolder) holder).imageView);
        return;
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }
}

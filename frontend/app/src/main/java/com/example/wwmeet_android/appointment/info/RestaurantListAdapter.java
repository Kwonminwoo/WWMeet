package com.example.wwmeet_android.appointment.info;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wwmeet_android.R;
import com.example.wwmeet_android.domain.Restaurant;


import java.util.ArrayList;
import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

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

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.item_progressbar);
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
        if(viewType == VIEW_TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_list, parent, false);
            return new ItemViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder){
            Restaurant restaurant = restaurantList.get(position);
            ((ItemViewHolder) holder).restaurantNameText.setText(restaurant.getRestaurantName());
            ((ItemViewHolder) holder).menuText.setText(restaurant.getMenu());
            ((ItemViewHolder) holder).addressText.setText(restaurant.getAddress());
            Glide.with(context)
                    .load(restaurant.getImageUrl())
                    .into(((ItemViewHolder) holder).imageView);
            return;
        } else if (holder instanceof LoadingViewHolder) {
            Log.e("e", "loading");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return restaurantList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

}

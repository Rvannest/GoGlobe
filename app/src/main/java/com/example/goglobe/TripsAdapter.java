package com.example.goglobe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {

    ArrayList<TripsDataSet> dataList;
    Context context;

    public TripsAdapter(ArrayList<TripsDataSet> data, Context context){
        this.dataList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TripsDataSet data = dataList.get(position);

        holder.tripNameView.setText(data.getTripName());
        //holder.tripLocationView.setText(data.getTripLocation());
        //holder.tripStartDateView.setText(data.getStartDate().toString());
        //holder.tripEndDateView.setText(data.getEndDate().toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TripDetailsActivity.class);
                intent.putExtra("trip_id", dataList.get(position).getTripID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tripNameView;
        TextView tripLocationView;
        TextView tripStartDateView;
        TextView tripEndDateView;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tripNameView = itemView.findViewById(R.id.trip_name_view);
//            tripLocationView = itemView.findViewById(R.id.trip_location_view);
//            tripStartDateView = itemView.findViewById(R.id.start_date_view);
//            tripEndDateView = itemView.findViewById(R.id.end_date_view);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}


package com.example.coursework.Adapters;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework.R;
import com.example.coursework.Models.Observation;

public class ObservationAdapter extends RecyclerView.Adapter<ObservationAdapter.ObservationViewHolder>{
    private List<Observation> observations;
    private OnItemClickListener listener;

    public ObservationAdapter(List<Observation> observations) {
        this.observations = observations;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hike_card, parent, false);
        return new ObservationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        Observation observation = observations.get(position);
        holder.hikeName.setText(observation.name);
        holder.hikeDetails.setText(observation.tob);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    @Override
    public int getItemCount() {
        return observations.size();
    }

    public static class ObservationViewHolder extends RecyclerView.ViewHolder {
        TextView hikeName, hikeDetails;

        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            hikeName = itemView.findViewById(R.id.hikeName);
            hikeDetails = itemView.findViewById(R.id.hikeDetails);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

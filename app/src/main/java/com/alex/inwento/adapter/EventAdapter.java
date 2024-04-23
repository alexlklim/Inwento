package com.alex.inwento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.http.inventory.EventDTO;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.OrderViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<EventDTO> eventList;

    public EventAdapter(List<EventDTO> events, OnItemClickListener onItemClickListener) {
        this.eventList = events;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_events, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        EventDTO event = eventList.get(position);

        holder.reTitle.setText(event.getBranch());
        holder.reUser.setText("by " + event.getUserName());
        holder.reAmount.setText(event.getScannedProductAmount() + " / " + event.getTotalProductAmount());
        holder.reUnknown.setText("unknown " + event.getUnknownProductAmount());

        if (event.getScannedProductAmount() == event.getTotalProductAmount()) holder.reStatus.setImageResource(R.drawable.ic_done);
        else holder.reStatus.setImageResource(R.drawable.ic_in_process);


        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(event.getId()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView reTitle, reUser, reAmount, reUnknown;
        ImageView reStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            reTitle = itemView.findViewById(R.id.reTitle);
            reUser = itemView.findViewById(R.id.reUser);
            reAmount = itemView.findViewById(R.id.reAmount);
            reUnknown = itemView.findViewById(R.id.reUnknown);
            reStatus = itemView.findViewById(R.id.reStatus);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int orderId);
    }
}


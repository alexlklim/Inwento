package com.alex.inwento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.database.domain.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.OrderViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private List<Event> eventList;

    public EventAdapter(List<Event> events, OnItemClickListener onItemClickListener) {
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
        Event event = eventList.get(position);

        holder.branch.setText(event.getBranch());
        holder.username.setText("by " + event.getUsername());
        holder.products.setText(event.getScannedProductsAmount() + " / " + event.getTotalProductsAmount());
        holder.unknown.setText("unknown " + event.getUnknownProductsAmount());

        System.out.println("DDDDDDDDDDDDDDDDDDDDD: " + event.getUnknownProducts());
        if (event.getScannedProductsAmount() == event.getTotalProductsAmount()) {
            holder.event_status.setImageResource(R.drawable.ic_done);
        } else {
            holder.event_status.setImageResource(R.drawable.ic_in_process);
        }

        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemClick(event.getId()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView branch, username, products, unknown;
        ImageView event_status;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            branch = itemView.findViewById(R.id.rve_branch);
            username = itemView.findViewById(R.id.rve_username);
            products = itemView.findViewById(R.id.rve_products);
            unknown = itemView.findViewById(R.id.rve_unknown);
            event_status = itemView.findViewById(R.id.rve_event_status);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int orderId);
    }
}


package com.alex.inwento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.http.inventory.UnknownProductDTO;

import java.util.List;

public class UnknownProductAdapter extends RecyclerView.Adapter<UnknownProductAdapter.OrderViewHolder> {
    private List<UnknownProductDTO> unknownProductList;



    // Method to update the data set

    public void updateData(List<UnknownProductDTO> newUnknownProducts) {
        this.unknownProductList.clear(); // Clear existing data
        this.unknownProductList.addAll(newUnknownProducts); // Add new data
    }

    public UnknownProductAdapter(List<UnknownProductDTO> unknownProducts) {
        this.unknownProductList = unknownProducts;
    }


    @NonNull
    @Override
    public UnknownProductAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_unknown_products, parent, false);
        return new UnknownProductAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnknownProductAdapter.OrderViewHolder holder, int position) {
        UnknownProductDTO unknownProduct = unknownProductList.get(position);


        int id = position + 1;
        holder.id.setText(String.valueOf(id));

        holder.code.setText(unknownProduct.getCode());
    }

    @Override
    public int getItemCount() {
        return unknownProductList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView id, code;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.rvup_id);
            code = itemView.findViewById(R.id.rvup_code);
        }
    }


}

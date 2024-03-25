package com.alex.inwento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.database.domain.UnknownProduct;
import com.alex.inwento.managers.DateMng;

import java.time.LocalDate;
import java.util.List;

public class UnknownProductAdapter extends RecyclerView.Adapter<UnknownProductAdapter.OrderViewHolder> {
    private final UnknownProductAdapter.OnItemUnknownProductClickListener onItemClickListener;
    private List<UnknownProduct> unknownProductList;

    public UnknownProductAdapter(
            List<UnknownProduct> unknownProducts,
            UnknownProductAdapter.OnItemUnknownProductClickListener onItemClickListener) {
        this.unknownProductList = unknownProducts;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UnknownProductAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recucler_unknown_products, parent, false);
        return new UnknownProductAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnknownProductAdapter.OrderViewHolder holder, int position) {
        UnknownProduct unknownProduct = unknownProductList.get(position);

        holder.id.setText(String.valueOf(unknownProduct.getId()));
        holder.code.setText("Code: " + unknownProduct.getCode());
        // set date
        holder.date.setText(DateMng.convertDateToString(LocalDate.now()));
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemUnknownProductClick(unknownProduct.getId()));
    }

    @Override
    public int getItemCount() {
        return unknownProductList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView id, code, date;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.rvup_id);
            code = itemView.findViewById(R.id.rvup_code);
            date = itemView.findViewById(R.id.rvup_date);
        }
    }

    public interface OnItemUnknownProductClickListener {
        void onItemUnknownProductClick(int orderId);
    }

}

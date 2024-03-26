package com.alex.inwento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.database.domain.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.OrderViewHolder> {

    private final ProductAdapter.OnItemProductClickListener onItemClickListener;
    private List<Product> productList;

    public ProductAdapter(List<Product> products, ProductAdapter.OnItemProductClickListener onItemClickListener) {
        this.productList = products;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProductAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_products, parent, false);
        return new ProductAdapter.OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.OrderViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.title.setText(product.getTitle());
        holder.code.setText("code: " + product.getBarCode());
        if (product.getInventoryStatus().equalsIgnoreCase("SCANNED")) holder.status.setText("OK");
         else holder.status.setText("W TOKU");




        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemProductClick(product.getId()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView title, code, status;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rvp_title);
            code = itemView.findViewById(R.id.rvp_code);
            status = itemView.findViewById(R.id.rvp_status);
        }
    }

    public interface OnItemProductClickListener {
        void onItemProductClick(int orderId);
    }
}

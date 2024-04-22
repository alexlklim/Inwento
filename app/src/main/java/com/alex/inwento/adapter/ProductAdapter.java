package com.alex.inwento.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.http.inventory.ProductShortDTO;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.OrderViewHolder> {

    private final ProductAdapter.OnItemProductClickListener onItemClickListener;
    private List<ProductShortDTO> productList;
    private Boolean isInventory, isScanned;

    public ProductAdapter(
            Boolean isInventory, Boolean isScanned,
            List<ProductShortDTO> productList,
            ProductAdapter.OnItemProductClickListener onItemClickListener) {
        this.productList = productList;
        this.isScanned = isScanned;
        this.isInventory = isInventory;
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
        ProductShortDTO product = productList.get(position);

        holder.rpTitle.setText(product.getTitle());
        holder.rpBarCode.setText("code: " + product.getBarCode());
        holder.rpRfidCode.setText("rfid: " + product.getRfidCode());
        holder.rpBranch.setText(product.getBranch());
        holder.rpLocation.setText(product.getLocation());
        if (isInventory) {
            if (isScanned) holder.rpIsScanned.setImageResource(R.drawable.ic_done);
            else holder.rpIsScanned.setImageResource(R.drawable.ic_in_process);
            holder.rpIsScanned.setVisibility(View.VISIBLE);
        } else holder.rpIsScanned.setVisibility(View.INVISIBLE);
        holder.itemView.setOnClickListener(view -> onItemClickListener.onItemProductClick(product.getId()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView rpTitle, rpBarCode, rpRfidCode, rpBranch, rpLocation;
        ImageButton rpIsScanned;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            rpTitle = itemView.findViewById(R.id.rpTitle);
            rpBarCode = itemView.findViewById(R.id.rpBarCode);
            rpRfidCode = itemView.findViewById(R.id.rpRfidCode);
            rpBranch = itemView.findViewById(R.id.rpBranch);
            rpLocation = itemView.findViewById(R.id.rpLocation);
            rpIsScanned = itemView.findViewById(R.id.rpIsScanned);
        }
    }

    public interface OnItemProductClickListener {
        void onItemProductClick(int orderId);
    }
}

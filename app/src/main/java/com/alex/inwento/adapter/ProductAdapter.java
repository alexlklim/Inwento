package com.alex.inwento.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.inwento.R;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.http.inventory.UnknownProductDTO;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.OrderViewHolder> {

    private final OnItemProductClickListener onItemClickListener;
    private List<ProductShortDTO> productList;
    private final Boolean isInventory, isScanned;
    private boolean isHandlingScanEvent = false;
    private final Context context;

    public ProductAdapter(
            Context context,
            Boolean isInventory,
            Boolean isScanned,
            List<ProductShortDTO> productList,
            OnItemProductClickListener onItemClickListener) {
        this.context = context;
        this.productList = productList;
        this.isScanned = isScanned;
        this.isInventory = isInventory;
        this.onItemClickListener = onItemClickListener;
    }

    public void updateData(List<ProductShortDTO> productShortDTOs) {
        this.productList.clear();
        this.productList.addAll(productShortDTOs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_products, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        ProductShortDTO product = productList.get(position);

        holder.rpTitle.setText(product.getTitle());
        holder.rpBranch.setText(product.getBranch());
        holder.rpProducer.setText(product.getProducer());
        holder.rpLocation.setText(product.getLocation());

        if (isInventory) {
            if (isScanned) {
                holder.rpIsScanned.setImageResource(R.drawable.ic_done);
                holder.rpIsScanned.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.result_success), PorterDuff.Mode.SRC_IN);
            } else {
                holder.rpIsScanned.setImageResource(R.drawable.ic_in_process);
                holder.rpIsScanned.setColorFilter(holder.itemView.getContext().getResources().getColor(R.color.result_fail), PorterDuff.Mode.SRC_IN);
            }
        } else {
            holder.rpLLImageResult.setVisibility(View.INVISIBLE);
            ViewGroup parentView = (ViewGroup) holder.rpLLImageResult.getParent();
            if (parentView != null) parentView.removeView(holder.rpLLImageResult);
        }

        holder.itemView.setOnClickListener(view -> {
            if (!isHandlingScanEvent) {
                onItemClickListener.onItemProductClick(product.getId());
            }
        });
    }

    public void setHandlingScanEvent(boolean handlingScanEvent) {
        isHandlingScanEvent = handlingScanEvent;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView rpTitle, rpBranch, rpLocation, rpProducer;
        ImageButton rpIsScanned;
        private final LinearLayout rpLLImageResult;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            rpTitle = itemView.findViewById(R.id.rpTitle);
            rpBranch = itemView.findViewById(R.id.rpBranch);
            rpLocation = itemView.findViewById(R.id.rpLocation);
            rpProducer = itemView.findViewById(R.id.rpProducer);
            rpIsScanned = itemView.findViewById(R.id.rpIsScanned);
            rpLLImageResult = itemView.findViewById(R.id.rpllImageResult);
        }
    }

    public interface OnItemProductClickListener {
        void onItemProductClick(int orderId);
    }
}

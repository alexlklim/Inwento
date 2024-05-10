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

    private final ProductAdapter.OnItemProductClickListener onItemClickListener;
    private List<ProductShortDTO> productList;
    private Boolean isInventory, isScanned;
    private boolean isHandlingScanEvent = false;
    Context context;

    public void updateData(List<ProductShortDTO> productShortDTOs) {
        this.productList.clear();
        this.productList.addAll(productShortDTOs);
        notifyDataSetChanged();
    }


    public ProductAdapter(
            Context context,
            Boolean isInventory, Boolean isScanned,
            List<ProductShortDTO> productList,
            ProductAdapter.OnItemProductClickListener onItemClickListener) {
        this.context = context;
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
            if (isScanned) {
                holder.rpIsScanned.setImageResource(R.drawable.ic_done);
                holder.rpIsScanned.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
            } else {
                holder.rpIsScanned.setImageResource(R.drawable.ic_in_process);
                holder.rpIsScanned.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            }
            holder.rpLLLocation.findViewById(R.id.rpBranch).setVisibility(View.GONE);

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
        TextView rpTitle, rpBarCode, rpRfidCode, rpBranch, rpLocation;
        ImageButton rpIsScanned;
        private final LinearLayout rpLLLocation, rpLLImageResult;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            rpTitle = itemView.findViewById(R.id.rpTitle);
            rpBarCode = itemView.findViewById(R.id.rpBarCode);
            rpRfidCode = itemView.findViewById(R.id.rpRfidCode);
            rpBranch = itemView.findViewById(R.id.rpBranch);
            rpLocation = itemView.findViewById(R.id.rpLocation);
            rpIsScanned = itemView.findViewById(R.id.rpIsScanned);
            rpLLLocation = itemView.findViewById(R.id.rpllLocation);
            rpLLImageResult = itemView.findViewById(R.id.rpllImageResult);

        }
    }

    public interface OnItemProductClickListener {
        void onItemProductClick(int orderId);
    }
}

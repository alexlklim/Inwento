package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.dto.ProductDto;


public class ProductScannedDialog extends AppCompatDialogFragment {
    private static final String TAG = "ProductScannedDialog";
    private ProductScannedListener productScannedListener;

    private FragmentActivity fragmentActivity;
    private ProductDto productDto;

    TextView title, desc, code, price, liable, receiver, warning;
    Button btnSave;

    boolean isCurrentBranch;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_product, null);
        builder.setView(view).setTitle("Product");

        title = view.findViewById(R.id.dp_title);
        desc = view.findViewById(R.id.dp_desc);
        code = view.findViewById(R.id.dp_code);
        price = view.findViewById(R.id.dp_price);
        liable = view.findViewById(R.id.dp_liable);
        receiver = view.findViewById(R.id.dp_receiver);
        btnSave = view.findViewById(R.id.dp_save);
        warning = view.findViewById(R.id.dp_warning);

        fragmentActivity = requireActivity();

        title.setText(productDto.getTitle());
        desc.setText(productDto.getDescription());
        code.setText(productDto.getBar_code());
        price.setText("Price: " + productDto.getPrice());
        liable.setText(productDto.getLiable());
        receiver.setText(productDto.getReceiver());

        if (isCurrentBranch){
            ViewGroup parentView = (ViewGroup) warning.getParent();
            parentView.removeView(warning);
        } else {
            btnSave.setText("przesunięcie");
        }


        btnSave.setOnClickListener(v -> {
            if (productScannedListener != null){
                productScannedListener.onProductSaved(productDto.getBar_code());
            }
            dismiss();
        });

        return builder.create();
    }


    public static ProductScannedDialog newInstance(ProductScannedListener listener, ProductDto productDto, boolean isCurrentBranch) {
        ProductScannedDialog dialog = new ProductScannedDialog();
        dialog.productDto = productDto;
        dialog.productScannedListener = listener;
        dialog.isCurrentBranch = isCurrentBranch;
        return dialog;
    }



    public interface ProductScannedListener {
        void onProductSaved(String barCode);
    }
}

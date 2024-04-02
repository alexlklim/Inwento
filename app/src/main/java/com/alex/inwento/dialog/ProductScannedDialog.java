package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.dto.ProductDto;
import com.alex.inwento.tasks.PostProductsTask;

import java.util.Arrays;
import java.util.List;


public class ProductScannedDialog extends AppCompatDialogFragment
        implements PostProductsTask.ProductUploadListener {
    private static final String TAG = "ProductScannedDialog";
    private ProductScannedListener productScannedListener;

    private FragmentActivity fragmentActivity;
    private ProductDto productDto;

    TextView title, desc, code, price, liable, receiver, warning;
    Button btnSave;
    int eventId;
    String token;

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

        if (isCurrentBranch) {
            ViewGroup parentView = (ViewGroup) warning.getParent();
            parentView.removeView(warning);
        } else {
            btnSave.setText("przesunięcie");
        }


        btnSave.setOnClickListener(v -> {
            if (productScannedListener != null) {
                productScannedListener.onProductSaved(productDto.getBar_code());
                new PostProductsTask(this, Arrays.asList(productDto.getBar_code()), eventId, token ).execute();
            }
        });

        return builder.create();
    }


    public static ProductScannedDialog newInstance(
            ProductScannedListener listener,
            ProductDto productDto,
            boolean isCurrentBranch,
            int eventId,
            String token) {
        ProductScannedDialog dialog = new ProductScannedDialog();
        dialog.productDto = productDto;
        dialog.productScannedListener = listener;
        dialog.isCurrentBranch = isCurrentBranch;
        dialog.eventId = eventId;
        dialog.token = token;
        return dialog;
    }

    @Override
    public void onUploadSuccess(Boolean answer) {
        Log.i(TAG, "onUploadSuccess: ");
        Toast.makeText(requireActivity(), "Product added", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onUploadFailure(String errorMessage) {
        Log.i(TAG, "onUploadFailure: ");
        Toast.makeText(requireActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
        dismiss();
    }


    public interface ProductScannedListener {
        void onProductSaved(String barCode);
    }
}

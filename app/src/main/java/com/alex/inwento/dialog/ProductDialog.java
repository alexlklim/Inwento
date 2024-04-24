package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alex.inwento.R;
import com.alex.inwento.http.inventory.ProductDTO;

public class ProductDialog extends AppCompatDialogFragment {

    private ProductDialog.ProductDialogListener productDialogListener;
    private ProductDTO productDTO;
    private Boolean isInventory;
    private String currentBranch;
    Button dpBtnOk;
    TextView dpTitle, dpDesc, dpCode, dpRfid, dpBranch, dpLocation, dpLiable, dpReceiver, dpWarning;

    public static ProductDialog newInstance(
            ProductDialog.ProductDialogListener listener,
            String currentBranch,
            Boolean isInventory,
            ProductDTO productDTO) {
        ProductDialog dialog = new ProductDialog();
        dialog.productDialogListener = listener;
        dialog.productDTO = productDTO;
        dialog.currentBranch = currentBranch;
        dialog.isInventory = isInventory;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_product, null);
        builder.setView(view).setTitle("Product");

        dpTitle = view.findViewById(R.id.dpTitle);
        dpDesc = view.findViewById(R.id.dpDesc);
        dpCode = view.findViewById(R.id.dpCode);
        dpRfid = view.findViewById(R.id.dpRfid);
        dpBranch = view.findViewById(R.id.dpBranch);
        dpLocation = view.findViewById(R.id.dpLocation);
        dpLiable = view.findViewById(R.id.dpLiable);
        dpReceiver = view.findViewById(R.id.dpReceiver);
        dpBtnOk = view.findViewById(R.id.dpBtnOk);
        dpWarning = view.findViewById(R.id.dpWarning);


        dpTitle.setText(productDTO.getTitle());
        dpDesc.setText(productDTO.getDescription());
        dpCode.setText(productDTO.getBarCode());
        dpRfid.setText(productDTO.getRfidCode());
        dpBranch.setText(productDTO.getBranch());
        dpLocation.setText(productDTO.getLocation());
        dpLiable.setText(productDTO.getLiableName());
        dpReceiver.setText(productDTO.getReceiver());

        if (!isInventory) {
            ViewGroup parentView = (ViewGroup) dpWarning.getParent();
            parentView.removeView(dpWarning);
            dpBtnOk.setOnClickListener(v -> dismiss());
        }
        else {
            // CHANGE THE BUTTON TO ZESKANOWANE IF INVENTORY TRUE
            dpBtnOk.setText(R.string.scanned);
            dpBtnOk.setOnClickListener(v -> {
                productDialogListener.onSentScannedProduct(productDTO);
                // ADD LISTENER
                dismiss();
            });
            if (currentBranch.equals(productDTO.getBranch())){
                // DELETE WARNING IF BRANCHES ARE THE SAME
                ViewGroup parentView = (ViewGroup) dpWarning.getParent();
                parentView.removeView(dpWarning);
            } else {
                // SET BUTTON TO MOVE IF BRANCHES ARE NOT THE SAME
                dpBtnOk.setText(R.string.move);
            }
        }

        return builder.create();
    }





    public interface ProductDialogListener {
        void onSentScannedProduct(ProductDTO productDTO);
    }

}

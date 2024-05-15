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
    private String currentBranch, currentLocation;
    Button dpBtnOk;
    TextView dpTitle, dpDesc, dpCode, dpRfid, dpBranch, dpLocation, dpLiable, dpReceiver, dpWarning, dpWarningLocation;

    public static ProductDialog newInstance(
            ProductDialog.ProductDialogListener listener,
            String currentBranch,
            String currentLocation,
            Boolean isInventory,
            ProductDTO productDTO) {
        ProductDialog dialog = new ProductDialog();
        dialog.productDialogListener = listener;
        dialog.productDTO = productDTO;
        dialog.currentBranch = currentBranch;
        dialog.currentLocation = currentLocation;
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
        dpWarningLocation = view.findViewById(R.id.dpWarningLocation);


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

            if (currentLocation.equalsIgnoreCase("Wszystkie") ||
                    !currentLocation.equalsIgnoreCase(productDTO.getLocation()) ||
                    !currentBranch.equalsIgnoreCase(productDTO.getBranch())
            ){


                if (!currentLocation.equalsIgnoreCase(productDTO.getLocation())){
                    dpWarningLocation.setText("Produckt zanjduje się w innej localizacji");
                    dpBtnOk.setText(R.string.move);
                }
                if (currentLocation.equalsIgnoreCase("Wszystkie")){
                    dpWarningLocation.setText("Localizacjia nie została wybrana");
                    dpBtnOk.setText("Ok");
                }


            } else {
                ViewGroup parentViewLocation = (ViewGroup) dpWarningLocation.getParent();
                parentViewLocation.removeView(dpWarningLocation);
            }

            if (!currentBranch.equalsIgnoreCase(productDTO.getBranch())){
                dpBtnOk.setText("Ok");
            } else {
                ViewGroup parentViewBranch = (ViewGroup) dpWarning.getParent();
                parentViewBranch.removeView(dpWarning);
            }

        }

        return builder.create();
    }





    public interface ProductDialogListener {
        void onSentScannedProduct(ProductDTO productDTO);
    }

}

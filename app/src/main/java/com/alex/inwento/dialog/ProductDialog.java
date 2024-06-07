package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
    TextView dpTitle,
            dpBranch, dpLocation,
            dpProducer, dpSupplier, dpSerialNumber,
            dpLiable, dpReceiver,
            dpDocumentDate, dpWarrantyPeriod, dpInspectionDate,
            dpScrapDate, dpScrapReason,
            dpWarningBranch, dpWarningLocation;


    LinearLayout dpLLDataBlock, dpLLDocumentBlock, dpLLScrapBlock;

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
        builder.setView(view);

        System.out.println(productDTO);

        dpTitle = view.findViewById(R.id.dpTitle);

        dpBranch = view.findViewById(R.id.dpBranch);
        dpLocation = view.findViewById(R.id.dpLocation);

        dpLiable = view.findViewById(R.id.dpLiable);
        dpReceiver = view.findViewById(R.id.dpReceiver);

        dpLLDataBlock = view.findViewById(R.id.dpLLDataBlock);
        dpProducer = view.findViewById(R.id.dpProducer);
        dpSupplier = view.findViewById(R.id.dpSupplier);
        dpSerialNumber = view.findViewById(R.id.dpSerialNumber);

        dpLLDocumentBlock = view.findViewById(R.id.dpLLDocumentBlock);
        dpDocumentDate = view.findViewById(R.id.dpDocumentDate);
        dpWarrantyPeriod = view.findViewById(R.id.dpWarrantyPeriod);
        dpInspectionDate = view.findViewById(R.id.dpInspectionDate);

        dpLLScrapBlock = view.findViewById(R.id.dpLLScrapBlock);
        dpScrapDate = view.findViewById(R.id.dpScrapDate);
        dpScrapReason = view.findViewById(R.id.dpScrapReason);

        dpBtnOk = view.findViewById(R.id.dpBtnOk);

        dpWarningBranch = view.findViewById(R.id.dpWarningBranch);
        dpWarningLocation = view.findViewById(R.id.dpWarningLocation);


        dpTitle.setText(productDTO.getTitle());
        dpBranch.setText(productDTO.getBranch());
        dpLocation.setText(productDTO.getLocation());

        dpLiable.setText(productDTO.getLiableName());
        dpReceiver.setText(productDTO.getReceiver());

        if (!isInventory){
            dpProducer.setText(productDTO.getProducer());
            dpSupplier.setText(productDTO.getSupplier());
            dpSerialNumber.setText(productDTO.getSerialNumber());

            dpDocumentDate.setText(productDTO.getDocumentDate());
            dpWarrantyPeriod.setText(productDTO.getWarrantyPeriod());
            dpInspectionDate.setText(productDTO.getInspectionDate());
        } else{
            dpLLDataBlock.setVisibility(View.GONE);
            dpLLDocumentBlock.setVisibility(View.GONE);
        }

        if (productDTO.isScrapped()){

            dpScrapDate.setText(productDTO.getScrappingDate());
            dpScrapReason.setText(productDTO.getScrappingReason());
        } else{
            dpLLScrapBlock.setVisibility(View.GONE);
        }


        if (!isInventory) {
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
            if (!currentLocation.equalsIgnoreCase(productDTO.getLocation())){
                TextView dpWarningLocation = view.findViewById(R.id.dpWarningLocation);
                dpWarningLocation.setVisibility(View.VISIBLE);
                dpWarningLocation.setText("Produkt zanjduje się w innej lokalizacji");
                dpBtnOk.setText(R.string.move);
            }
            if (currentLocation.equalsIgnoreCase("Wybierz lokalizacjię")){
                TextView dpWarningLocation = view.findViewById(R.id.dpWarningLocation);
                dpWarningLocation.setVisibility(View.VISIBLE);
                dpWarningLocation.setText("Localizacjia nie została wybrana");
                dpBtnOk.setText("Ok");
            }

            if (!currentBranch.equalsIgnoreCase(productDTO.getBranch())){
                TextView dpWarningBranch = view.findViewById(R.id.dpWarningBranch);
                dpWarningBranch.setVisibility(View.VISIBLE);
                dpBtnOk.setText("Ok");
            }

        }

        return builder.create();
    }





    public interface ProductDialogListener {
        void onSentScannedProduct(ProductDTO productDTO);
    }

}
// numer seryiny, producent
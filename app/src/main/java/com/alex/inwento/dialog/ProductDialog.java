package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.http.inventory.ProductDTO;

public class ProductDialog extends AppCompatDialogFragment {

    private static final String TAG = "ProductDialog";

    private ProductDTO productDTO;
    TextView dpTitle, dpDesc, dpCode, dpRfid, dpBranch, dpLocation, dpLiable, dpReceiver, dpBtnOk;

    public static ProductDialog newInstance(ProductDTO productDTO) {
        ProductDialog dialog = new ProductDialog();
        dialog.productDTO = productDTO;
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


        dpTitle.setText(productDTO.getTitle());
        dpDesc.setText(productDTO.getDescription());
        dpCode.setText(productDTO.getBarCode());
        dpRfid.setText(productDTO.getRfidCode());
        dpBranch.setText(productDTO.getBranch());
        dpLocation.setText(productDTO.getLocation());
        dpLiable.setText(productDTO.getLiableName());
        dpReceiver.setText(productDTO.getReceiver());

        dpBtnOk.setOnClickListener(v -> dismiss());


        return builder.create();
    }


}

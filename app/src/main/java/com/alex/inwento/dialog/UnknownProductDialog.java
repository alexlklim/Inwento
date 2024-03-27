package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;

public class UnknownProductDialog extends AppCompatDialogFragment {
    private static final String TAG = "UnknownProductDialog";
    private UnknownProductDialog.UnknownProductScannedListener unknownProductScannedListener;
    private FragmentActivity fragmentActivity;
    TextView code;
    String barCode;
    Button btnSave;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_unknown_product, null);
        builder.setView(view).setTitle("Unknown Product");
        fragmentActivity = requireActivity();

        code = view.findViewById(R.id.dup_code);
        btnSave = view.findViewById(R.id.dup_save);
        code.setText(barCode);


        btnSave.setOnClickListener(v -> {
            if (unknownProductScannedListener != null) unknownProductScannedListener.onUnknownProductSaved(barCode);
            dismiss();
        });
        return builder.create();
    }


    public static UnknownProductDialog newInstance(UnknownProductDialog.UnknownProductScannedListener listener, String barCode) {
        UnknownProductDialog dialog = new UnknownProductDialog();
        dialog.barCode = barCode;
        dialog.unknownProductScannedListener = listener;
        return dialog;
    }


    public interface UnknownProductScannedListener {
        void onUnknownProductSaved(String barCode);
    }
}

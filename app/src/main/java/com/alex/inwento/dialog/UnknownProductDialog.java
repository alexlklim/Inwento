package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.tasks.PostProductsTask;

import java.util.Arrays;

public class UnknownProductDialog extends AppCompatDialogFragment
        implements PostProductsTask.ProductUploadListener {
    private static final String TAG = "UnknownProductDialog";
    private UnknownProductDialog.UnknownProductScannedListener unknownProductScannedListener;
    private FragmentActivity fragmentActivity;
    TextView code;
    String barCode;
    int eventId;
    String token;
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
            if (unknownProductScannedListener != null)
                unknownProductScannedListener.onUnknownProductSaved(barCode);
            new PostProductsTask(this, Arrays.asList(barCode), eventId, token).execute();
        });
        return builder.create();
    }


    public static UnknownProductDialog newInstance(
            UnknownProductDialog.UnknownProductScannedListener listener,
            String barCode,
            int eventId,
            String token) {
        UnknownProductDialog dialog = new UnknownProductDialog();
        dialog.barCode = barCode;
        dialog.unknownProductScannedListener = listener;
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


    public interface UnknownProductScannedListener {
        void onUnknownProductSaved(String barCode);
    }
}

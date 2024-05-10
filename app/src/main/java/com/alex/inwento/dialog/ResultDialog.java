package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.alex.inwento.R;

public class ResultDialog extends AppCompatDialogFragment {
    private ResultDialogListener mListener;

    private String text;
    private Boolean isSuccess;
    TextView drText;
    ImageButton drImage;
    Button drBtnOk;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_result, null);
        builder.setView(view).setTitle("");

        drText = view.findViewById(R.id.drText);
        drImage = view.findViewById(R.id.drImage);
        drBtnOk = view.findViewById(R.id.drBtnOk);

        drText.setText(text);
        if (isSuccess) {
            drImage.setImageResource(R.drawable.ic_done);
            drImage.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_green_light));
        } else {
            drImage.setImageResource(R.drawable.ic_error);
            drImage.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_light));
        }

        drBtnOk.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onOkClicked();
            }
            dismiss();
        });

        return builder.create();
    }


    public static ResultDialog newInstance(String text, Boolean isSuccess, ResultDialogListener listener) {
        ResultDialog dialog = new ResultDialog();
        dialog.text = text;
        dialog.isSuccess = isSuccess;
        dialog.mListener = listener;
        return dialog;
    }


    public interface ResultDialogListener {
        void onOkClicked();
    }

}

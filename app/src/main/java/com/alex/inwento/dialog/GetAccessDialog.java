package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.alex.inwento.R;
import com.alex.inwento.managers.SettingsMng;

public class GetAccessDialog extends AppCompatDialogFragment {

    private GetAccessDialog.GetAccessDialogListener mListener;
    private SettingsMng settingsMng;

    EditText dgaAccessCode;
    Button dgaBtnGetAccess;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_get_access, null);
        builder.setView(view);

        dgaAccessCode = view.findViewById(R.id.dgaAccessCode);
        dgaBtnGetAccess = view.findViewById(R.id.dgaBtnGetAccess);



        dgaBtnGetAccess.setOnClickListener(v -> {
            if (settingsMng.getAccessCode().equals(dgaAccessCode.getText().toString())){
                mListener.onGetAccessDialog(true);
                dismiss();
            } else {
                mListener.onGetAccessDialog(false);
                dismiss();
            }
        });

        return builder.create();
    }


    public static GetAccessDialog newInstance(GetAccessDialog.GetAccessDialogListener listener, SettingsMng settingsMng) {
        GetAccessDialog dialog = new GetAccessDialog();
        dialog.settingsMng = settingsMng;
        dialog.mListener = listener;
        return dialog;
    }

    public interface GetAccessDialogListener {
        void onGetAccessDialog(Boolean result);
    }


}

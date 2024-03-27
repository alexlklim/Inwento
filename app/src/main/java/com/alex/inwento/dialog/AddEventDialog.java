package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.database.domain.Event;
import com.alex.inwento.tasks.GetBranchesAndEmpTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddEventDialog extends AppCompatDialogFragment
        implements GetBranchesAndEmpTask.OnDataReceivedListener {
    private static final String TAG = "AddEventDialog";
    private FragmentActivity fragmentActivity;

    private AddEventDialog.AddEventListener addEventListener;

    Button btnSave;
    EditText text;
    Spinner branchesSpinner;
    TextView liable;
    List<String> branchNames;



    public static AddEventDialog newInstance(AddEventDialog.AddEventListener listener, String barCode) {
        AddEventDialog dialog = new AddEventDialog();
        dialog.addEventListener = listener;
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        String token = args != null ? args.getString("token") : null;
        String firstname = args != null ? args.getString("firstname") : null;
        String lastname = args != null ? args.getString("lastname") : null;

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_event, null);
        builder.setView(view).setTitle("Add event");

        btnSave = view.findViewById(R.id.dae_save);
        text = view.findViewById(R.id.dae_text);
        liable = view.findViewById(R.id.dae_liable);
        branchesSpinner = (Spinner) view.findViewById(R.id.dae_branch);
        liable.setText(firstname + " " + lastname);



        btnSave.setOnClickListener(v -> {
            Log.e(TAG, "setOnClickListener: ");
            addEventListener.onAddEventSave(new Event());

            dismiss();
        });


        if (token != null) {
            new GetBranchesAndEmpTask(this, token).execute();
        }
        return builder.create();
    }



    private void initializeSpinner() {
        for (String st : branchNames) {
            Log.d(TAG, "Branch Name: " + st);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                branchNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchesSpinner.setAdapter(adapter);
    }


    public void setTokenAndUser(String token, String firstName, String lastName) {
        Bundle args = new Bundle();
        args.putString("token", token);
        args.putString("firstname", firstName);
        args.putString("lastname", lastName);
        setArguments(args);
    }

    @Override
    public void onDataReceived(Employee[] employees, Branch[] branches) {
        branchNames = Arrays.stream(branches)
                .map(Branch::getBranch)
                .collect(Collectors.toList());
        initializeSpinner();
    }

    @Override
    public void onError(String errorMessage) {

    }


    public interface AddEventListener {
        void onAddEventSave(Event event);
    }

}

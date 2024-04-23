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
import com.alex.inwento.tasks.AddEventTask;
import com.alex.inwento.tasks.GetBranchesAndEmpTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddEventDialog extends AppCompatDialogFragment
        implements
        GetBranchesAndEmpTask.OnDataReceivedListener,
        AddEventTask.AddEventListener {
    private static final String TAG = "AddEventDialog";
    private FragmentActivity fragmentActivity;

    private AddEventDialog.AddEventListener addEventListener;

    Button btnSave;
    EditText text;
    Spinner branchesSpinner;
    TextView liable;
    List<String> branchNames;

    List<Branch> branchList;


    String token, firstname, lastname;


    public static AddEventDialog newInstance(
            AddEventDialog.AddEventListener listener,
            String token,
            String firstname,
            String lastname
    ) {
        AddEventDialog dialog = new AddEventDialog();
        dialog.addEventListener = listener;
        dialog.token = token;
        dialog.firstname = firstname;
        dialog.lastname = lastname;
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
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
            // sent request to save new event
//            new AddEventTask(this, token, Branch.getIdByName(branchList, getSelectedBranch()), text.getText().toString()).execute();

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


    @Override
    public void onDataReceived(Employee[] employees, Branch[] branches) {
        Log.d(TAG, "onDataReceived: ");
        branchList = Arrays.asList(branches);
        branchNames = Arrays.stream(branches)
                .map(Branch::getBranch)
                .collect(Collectors.toList());
        initializeSpinner();
    }

    @Override
    public void onError(String errorMessage) {
        Log.d(TAG, "onError: ");

    }

    @Override
    public void onAddEventSuccess(Boolean answer) {
        Log.d(TAG, "onAddEventSuccess: ");
        addEventListener.onAddEventSave(true);
        dismiss();
    }

    @Override
    public void onAddEventFailure(String errorMessage) {
        Log.d(TAG, "onAddEventFailure: ");
        addEventListener.onAddEventSave(true);

        dismiss();
    }

    private String getSelectedBranch() {
        return branchesSpinner.getSelectedItem().toString();
    }


    public interface AddEventListener {
        void onAddEventSave(Boolean result);
    }

}

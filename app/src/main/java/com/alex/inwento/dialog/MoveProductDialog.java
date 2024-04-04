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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.alex.inwento.R;
import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.dto.ProductDto;
import com.alex.inwento.tasks.GetBranchesAndEmpTask;
import com.alex.inwento.tasks.GetProductTask;
import com.alex.inwento.tasks.MoveProductTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoveProductDialog
        extends
        AppCompatDialogFragment
        implements
        GetProductTask.GetProductByBarCodeListener,
        GetBranchesAndEmpTask.OnDataReceivedListener,
        MoveProductTask.ProductMoveListener {
    private static final String TAG = "AddEventDialog";

    private FragmentActivity fragmentActivity;


    private ProductDto productDto;
    private List<String> branchList;
    private List<Branch> branchObjects;
    private List<String> employeeList;
    private List<Employee> employeeObjects;

    String token, barCode;


    private TextView titleTextView, codeTextView;
    private Spinner branchSpinner, liableSpinner;
    private EditText receiverEditText;
    private Button btnMove;


    public static MoveProductDialog newInstance(
            String token, String barCode) {
        MoveProductDialog dialog = new MoveProductDialog();
        dialog.token = token;
        dialog.barCode = barCode;
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_move_product, null);
        builder.setView(view).setTitle("Pezesunięcie");
        fragmentActivity = requireActivity();

        titleTextView = view.findViewById(R.id.dmp_title);
        codeTextView = view.findViewById(R.id.dmp_code);
        branchSpinner = view.findViewById(R.id.dmp_branch);
        liableSpinner = view.findViewById(R.id.dmp_liable);
        receiverEditText = view.findViewById(R.id.dmp_receiver);
        btnMove = view.findViewById(R.id.dmp_move);


        new GetBranchesAndEmpTask(this, token).execute();
        new GetProductTask(this, token, barCode).execute();


        btnMove.setOnClickListener(v -> {
            new MoveProductTask(
                    this,
                    token,
                    productDto.getId(),
                    Branch.getBranchByName(branchSpinner.getSelectedItem().toString(), branchObjects),
                    Employee.getEmployeeByName(liableSpinner.getSelectedItem().toString(), employeeObjects),
                    receiverEditText.getText().toString())
                    .execute();


        });
        return builder.create();
    }

    @Override
    public void onDataReceived(Employee[] employees, Branch[] branches) {
        Log.i(TAG, "onDataReceived: ");
        branchObjects = Arrays.asList(branches);
        employeeObjects = Arrays.asList(employees);

        branchList = Arrays.stream(branches)
                .map(Branch::getBranch)
                .collect(Collectors.toList());

        employeeList = Arrays.stream(employees)
                .map(emp -> emp.getFirst_name() + " " + emp.getLast_name())
                .collect(Collectors.toList());

        initializeSpinner();
    }

    private void initializeSpinner() {
        Log.i(TAG, "initializeSpinner: ");
        ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                branchList
        );
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(adapterBranch);


        ArrayAdapter<String> adapterEmp = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                employeeList
        );
        adapterEmp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liableSpinner.setAdapter(adapterEmp);
    }


    @Override
    public void onError(String errorMessage) {
        Log.i(TAG, "onError: ");
        dismiss();
    }

    @Override
    public void onProductByBarCodeSuccess(ProductDto productDto) {
        Log.i(TAG, "onProductByBarCodeSuccess: ");
        this.productDto = productDto;
        titleTextView.setText(productDto.getTitle());
        codeTextView.setText(productDto.getBar_code());
        receiverEditText.setText(productDto.getReceiver());

        int branchIndex = branchList.indexOf(productDto.getBranch());
        if (branchIndex != -1) branchSpinner.setSelection(branchIndex);

        int liableIndex = employeeList.indexOf(productDto.getLiable());
        if (liableIndex != -1) liableSpinner.setSelection(liableIndex);
    }

    @Override
    public void onProductByBarCodeFailure(String errorMessage) {
        Log.i(TAG, "onProductByBarCodeFailure: ");
        dismiss();
    }


    @Override
    public void onProductMoveSuccess(Boolean answer) {
        Toast.makeText(requireContext(), "Przesunięcie producktu zrealizowane", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onProductMoveSuccess: ");
        dismiss();
    }

    @Override
    public void onProductMoveFailure(String errorMessage) {
        Toast.makeText(requireContext(), "Przesunięcie producktu nie powiodło się", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onProductMoveFailure: ");
        dismiss();
    }
}

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

import com.alex.inwento.R;
import com.alex.inwento.database.RoomDB;
import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.database.domain.ProductLocation;
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoveProductDialog
        extends
        AppCompatDialogFragment
implements
ResultDialog.ResultDialogListener{
    private static final String TAG = "AddEventDialog";


    private List<Branch> branchList;
    private List<ProductLocation> productLocationList;
    private List<Employee> employeeList;
    List<String> branches, locations, employees;

    String token;

    ProductDTO productDTO;

    private Spinner branchSpinner, liableSpinner, locationSpinner;
    private EditText receiverEditText;
    private RoomDB roomDB;


    public static MoveProductDialog newInstance(
            String token,
            ProductDTO productDTO,
            RoomDB roomDB) {
        MoveProductDialog dialog = new MoveProductDialog();
        dialog.token = token;
        dialog.productDTO = productDTO;
        dialog.roomDB = roomDB;
        return dialog;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateDialog: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_move_product, null);
        builder.setView(view).setTitle("Przesunięcie");

        TextView titleTextView = view.findViewById(R.id.dmp_title);
        TextView codeTextView = view.findViewById(R.id.dmp_code);
        Button btnMove = view.findViewById(R.id.dmp_move);

        branchSpinner = view.findViewById(R.id.dmp_branch);
        locationSpinner = view.findViewById(R.id.dmp_location);
        liableSpinner = view.findViewById(R.id.dmp_liable);
        receiverEditText = view.findViewById(R.id.dmp_receiver);

        branchList = roomDB.branchDAO().getAll();
        productLocationList = roomDB.locationDAO().getAll();
        employeeList = roomDB.employeeDAO().getAll();

        initializeSpinners();

        titleTextView.setText(productDTO.getTitle());
        codeTextView.setText(productDTO.getBarCode());
        receiverEditText.setText(productDTO.getReceiver());

        int branchIndex = branches.indexOf(productDTO.getBranch());
        if (branchIndex != -1) branchSpinner.setSelection(branchIndex);

        int locationIndex = locations.indexOf(productDTO.getLocation());
        if (locationIndex != -1) locationSpinner.setSelection(locationIndex);

        int liableIndex = employees.indexOf(productDTO.getLiableName());
        if (liableIndex != -1) liableSpinner.setSelection(liableIndex);

        btnMove.setOnClickListener(v -> sendProductUpdateRequest());
        return builder.create();
    }

    private void sendProductUpdateRequest() {
        Log.i(TAG, "sendProductUpdateRequest ");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Map<String, Object> updates = new HashMap<>();
        updates.put("id", productDTO.getId());
        updates.put("branch_id", roomDB.branchDAO().getBranchByName(branchSpinner.getSelectedItem().toString()).getId());
        updates.put("location_id", roomDB.locationDAO().getLocationByName(locationSpinner.getSelectedItem().toString()).getId());
        updates.put("liable_id", roomDB.employeeDAO().getEmployeeIdByFullName(liableSpinner.getSelectedItem().toString()));
        updates.put("receiver", receiverEditText.getText().toString());

        Call<Void> call = apiClient.putUpdatedProduct("Bearer " + token, updates);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "sendProductUpdateRequest is successful:");
                    requireActivity().runOnUiThread(() -> {
                        ResultDialog dialog = ResultDialog.newInstance(
                                "Product " + productDTO.getTitle() + " zastał przesunięty",
                                true,
                                MoveProductDialog.this);
                        dialog.show(getChildFragmentManager(), "MoveProductDialog");
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e(TAG, "sendProductUpdateRequest onFailure", t);
            }
        });
    }


    private void initializeSpinners() {
        Log.i(TAG, "initializeSpinner: ");
        branches = branchList.stream().map(Branch::getBranch).collect(Collectors.toList());
        locations = productLocationList.stream().map(ProductLocation::getLocation).collect(Collectors.toList());
        employees = employeeList.stream().map(emp -> emp.getFirstName() + " " + emp.getLastName()).collect(Collectors.toList());

        ArrayAdapter<String> adapterBranch = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, branches);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(adapterBranch);

        ArrayAdapter<String> adapterLocation = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, locations);
        adapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapterLocation);

        ArrayAdapter<String> adapterEmp = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, employees);
        adapterEmp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liableSpinner.setAdapter(adapterEmp);
    }


    @Override
    public void onOkClicked() {
        dismiss();
    }
}

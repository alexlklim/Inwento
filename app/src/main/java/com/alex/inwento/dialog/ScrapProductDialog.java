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



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScrapProductDialog
        extends
        AppCompatDialogFragment
        {


    private static final String TAG = "ScrapProductDialog";

    private FragmentActivity fragmentActivity;

    List<Integer> yearsList, daysList;
    List<String> monthList;


    String token, barCode;
    TextView titleTextView, descTextView, codeTextView, priceTextView, liableTextView, receiverTextView;
    Spinner daySpinner, monthSpinner, yearSpinner;
    EditText reasonEditText;
    Button btnScrap;


    public static ScrapProductDialog newInstance(
            String token, String barCode) {
        ScrapProductDialog dialog = new ScrapProductDialog();
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
        View view = inflater.inflate(R.layout.dialog_scrap_product, null);
        builder.setView(view).setTitle("Umożenie");
        fragmentActivity = requireActivity();


        titleTextView = view.findViewById(R.id.asp_title);
        descTextView = view.findViewById(R.id.asp_desc);
        codeTextView = view.findViewById(R.id.asp_code);
        priceTextView = view.findViewById(R.id.asp_price);
        liableTextView = view.findViewById(R.id.asp_liable);
        receiverTextView = view.findViewById(R.id.asp_receiver);
        daySpinner = view.findViewById(R.id.asp_data_day);
        monthSpinner = view.findViewById(R.id.asp_data_month);
        yearSpinner = view.findViewById(R.id.asp_data_year);
        reasonEditText = view.findViewById(R.id.asp_reason);
        btnScrap = view.findViewById(R.id.asp_scrap);


//        new GetProductTask(this, token, barCode).execute();
//
//        btnScrap.setOnClickListener(v -> {
//            new ScrapProductTask(this, token, productDto.getId(),
//                    Integer.parseInt(daySpinner.getSelectedItem().toString()),
//                    monthList.indexOf(monthSpinner.getSelectedItem().toString()),
//                    Integer.parseInt(yearSpinner.getSelectedItem().toString()),
//                    reasonEditText.getText().toString()
//            ).execute();
//
//        });
        return builder.create();
    }


//    @Override
//    public void onProductByBarCodeSuccess(ProductDto productDto) {
//        this.productDto = productDto;
//        titleTextView.setText(productDto.getTitle());
//        descTextView.setText(productDto.getDescription());
//        codeTextView.setText(productDto.getBar_code());
//        priceTextView.setText(String.valueOf(productDto.getPrice()));
//        liableTextView.setText(productDto.getLiable());
//        receiverTextView.setText(productDto.getReceiver());
//
//        yearsList = IntStream.rangeClosed(2020, 2024).boxed().collect(Collectors.toList());
//        monthList = Arrays.asList("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień");
//        daysList = IntStream.rangeClosed(1, 30).boxed().collect(Collectors.toList());
//
//        ArrayAdapter<Integer> adapterYears = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearsList);
//        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        yearSpinner.setAdapter(adapterYears);
//
//        ArrayAdapter<String> adapterMonths = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, monthList);
//        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        monthSpinner.setAdapter(adapterMonths);
//
//        ArrayAdapter<Integer> adapterDays = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, daysList);
//        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        daySpinner.setAdapter(adapterDays);
//
//        yearSpinner.setSelection(0);
//        monthSpinner.setSelection(0);
//        daySpinner.setSelection(0);
//    }
//
//    @Override
//    public void onProductByBarCodeFailure(String errorMessage) {
//        dismiss();
//    }
//
//    @Override
//    public void onProductScrapSuccess(Boolean answer) {
//        Toast.makeText(requireContext(), "Umożenie producktu zrealizowane", Toast.LENGTH_SHORT).show();
//        dismiss();
//    }
//
//    @Override
//    public void onProductScrapFailure(String errorMessage) {
//        Toast.makeText(requireContext(), "Umożenie producktu nie powiodło się", Toast.LENGTH_SHORT).show();
//        dismiss();
//    }
}

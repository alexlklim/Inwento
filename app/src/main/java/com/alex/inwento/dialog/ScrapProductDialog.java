package com.alex.inwento.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
import com.alex.inwento.http.APIClient;
import com.alex.inwento.http.RetrofitClient;
import com.alex.inwento.http.inventory.ProductDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrapProductDialog
        extends AppCompatDialogFragment
        implements ResultDialog.ResultDialogListener {
    private static final String TAG = "ScrapProductDialog";

    TextView dspTitle, dspCode, dspLiable, dspReceiver, dspWarningScrap;
    Spinner dspDataDay, dspDataMonth, dspDataYear;
    EditText dspReason;
    Button dscBtnScrap;


    List<Integer> yearsList, daysList;
    List<String> monthList;


    String token;
    private ProductDTO productDTO;
    RoomDB roomDB;

    public static ScrapProductDialog newInstance(
            String token,
            ProductDTO productDTO,
            RoomDB roomDB) {
        ScrapProductDialog dialog = new ScrapProductDialog();
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
        View view = inflater.inflate(R.layout.dialog_scrap_product, null);
        builder.setView(view);


        dspTitle = view.findViewById(R.id.dspTitle);
        dspCode = view.findViewById(R.id.dspCode);
        dspLiable = view.findViewById(R.id.dspLiable);
        dspReceiver = view.findViewById(R.id.dspReceiver);
        dspReason = view.findViewById(R.id.dspReason);
        dspDataDay = view.findViewById(R.id.dspDataDay);
        dspDataMonth = view.findViewById(R.id.dspDataMonth);
        dspDataYear = view.findViewById(R.id.dspDataYear);
        dscBtnScrap = view.findViewById(R.id.dscBtnScrap);
        dspWarningScrap = view.findViewById(R.id.dspWarningScrap);


        initializeSpinners();
        dspTitle.setText(productDTO.getTitle());
        dspCode.setText("code: " + productDTO.getBarCode());
        dspLiable.setText(productDTO.getLiableName());
        dspReceiver.setText(productDTO.getReceiver());


        if (productDTO.isScrapped()){
            TextView dpWarningScrap = view.findViewById(R.id.dspWarningScrap);
            dpWarningScrap.setVisibility(View.VISIBLE);
        }

        dscBtnScrap.setOnClickListener(v -> sendProductUpdateRequest());
        return builder.create();
    }


    public void initializeSpinners() {

        yearsList = IntStream.rangeClosed(2020, 2024).boxed().collect(Collectors.toList());
        monthList = Arrays.asList("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień");

        ArrayAdapter<Integer> adapterYears = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, yearsList);
        adapterYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dspDataYear.setAdapter(adapterYears);

        ArrayAdapter<String> adapterMonths = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, monthList);
        adapterMonths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dspDataMonth.setAdapter(adapterMonths);
        
        dspDataYear.setSelection(0);
        dspDataMonth.setSelection(0);

        initDaysSpinner();

        LocalDate today = LocalDate.now();
        int year = today.getYear(); // Get the current year
        int month = today.getMonthValue(); // Get the current month (1-based)


        dspDataYear.setSelection(yearsList.indexOf(year));
        dspDataMonth.setSelection(month - 1);


        dspDataYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                initDaysSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        dspDataMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                initDaysSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    
    private void initDaysSpinner(){
        int year = Integer.parseInt(dspDataYear.getSelectedItem().toString());
        int monthIndex = monthList.indexOf(dspDataMonth.getSelectedItem().toString());

        YearMonth yearMonth = YearMonth.of(year, monthIndex + 1);
        int daysInMonth = yearMonth.lengthOfMonth();


        daysList = IntStream.rangeClosed(1, daysInMonth).boxed().collect(Collectors.toList());
        ArrayAdapter<Integer> adapterDays = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, daysList);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dspDataDay.setAdapter(adapterDays);


        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        System.out.println(day);
        dspDataDay.setSelection(daysList.indexOf(day));

    }


    private void sendProductUpdateRequest() {
        Log.i(TAG, "sendProductUpdateRequest ");
        APIClient apiClient = RetrofitClient.getRetrofitInstance().create(APIClient.class);
        Map<String, Object> updates = new HashMap<>();
        String monthString, dayString;


        int year = Integer.parseInt(dspDataYear.getSelectedItem().toString());
        int month = monthList.indexOf(dspDataMonth.getSelectedItem())+1;
        int day = Integer.parseInt(dspDataDay.getSelectedItem().toString());
        if (month<10) monthString = "0" + month;
        else  monthString = String.valueOf(month);
        if (day<10) dayString = "0" + day;
        else dayString = String.valueOf(day);

        String date = year + "-" + monthString + "-" + dayString;
        updates.put("id", productDTO.getId());
        updates.put("scrapping", true);
        updates.put("scrapping_date", date);
        updates.put("scrapping_reason", dspReason.getText().toString());

        System.out.println("TOKEN : " + token);
        System.out.println("CHANGES : " + updates);

        Call<Void> call = apiClient.putUpdatedProduct("Bearer " + token, updates);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "sendProductUpdateRequest is successful:");
                    requireActivity().runOnUiThread(() -> {
                        ResultDialog dialog = ResultDialog.newInstance(
                                "Product\n" + productDTO.getTitle() + "\nzostał skutecznie umożony",
                                true,
                                ScrapProductDialog.this);
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


    @Override
    public void onOkClicked() {
        dismiss();
    }


}

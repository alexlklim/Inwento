package com.alex.inwento.http.inventory;


import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.database.domain.Employee;
import com.alex.inwento.database.domain.ProductLocation;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
    @SerializedName("branches")
    private List<Branch> branches;
    @SerializedName("employees")
    private List<Employee> employees;
    @SerializedName("locations")
    private List<ProductLocation> productLocations;

}

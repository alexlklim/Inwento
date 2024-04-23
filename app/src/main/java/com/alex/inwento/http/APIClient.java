package com.alex.inwento.http;

import com.alex.inwento.database.domain.Branch;
import com.alex.inwento.http.auth.AuthDTO;
import com.alex.inwento.http.auth.LoginDTO;
import com.alex.inwento.http.auth.RefreshTokenDTO;
import com.alex.inwento.http.inventory.InventoryDTO;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.http.inventory.ProductShortDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIClient {


    @POST("v1/auth/login")
    Call<AuthDTO> getAuthDTOLogin(@Body LoginDTO credentials);


    @POST("v1/auth/refresh")
    Call<AuthDTO> getAuthDTORefresh(@Body RefreshTokenDTO credentials);

    @GET("v1/inventory/current")
    Call<InventoryDTO> getCurrentInventory(@Header("Authorization") String authorization);


    @GET("v1/products/all/emp/true")
    Call<List<ProductShortDTO>> getShortProducts(
            @Header("Authorization") String authorization
    );

    @GET("v1/company")
    Call<String> get(@Header("Authorization") String authorization);


    @GET("v1/products/{id}")
    Call<ProductDTO> getFullProductById(
            @Header("Authorization") String authorization,
            @Path("id") long productId
    );

    @GET("v1/products/filter/unique/{bar_code}/{rfid_code}/null/null")
    Call<ProductDTO> getFullProductByCode(
            @Header("Authorization") String authorization,
            @Path("bar_code") String barCode,
            @Path("rfid_code") String rfid_code
    );

    @GET("v1/company/loc/branch")
    Call<List<Branch>> getBranches(@Header("Authorization") String authorization);

}
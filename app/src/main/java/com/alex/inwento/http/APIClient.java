package com.alex.inwento.http;

import com.alex.inwento.http.auth.AuthDTO;
import com.alex.inwento.http.auth.LoginDTO;
import com.alex.inwento.http.auth.RefreshTokenDTO;
import com.alex.inwento.http.inventory.DataDTO;
import com.alex.inwento.http.inventory.EventDTO;
import com.alex.inwento.http.inventory.InventoryDTO;
import com.alex.inwento.http.inventory.ProductDTO;
import com.alex.inwento.http.inventory.ProductShortDTO;
import com.alex.inwento.util.Endpoints;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIClient {


    @POST(Endpoints.LOGIN)
    Call<AuthDTO> getAuthDTOLogin(
            @Body LoginDTO credentials
    );


    @POST(Endpoints.REFRESH_TOKEN)
    Call<AuthDTO> getAuthDTORefresh(
            @Body RefreshTokenDTO credentials
    );

    @GET(Endpoints.GET_CURRENT_INVENTORY)
    Call<InventoryDTO> getCurrentInventory(
            @Header("Authorization") String authorization
    );

    @GET(Endpoints.GET_EVENT_BY_ID)
    Call<EventDTO> getEventById(
            @Header("Authorization") String authorization,
            @Path("event_id") long eventId
    );


    @GET(Endpoints.GET_SHORT_PRODUCTS)
    Call<List<ProductShortDTO>> getShortProducts(
            @Header("Authorization") String authorization
    );


    @GET(Endpoints.GET_FULL_PRODUCT_BY_ID)
    Call<ProductDTO> getFullProductById(
            @Header("Authorization") String authorization,
            @Path("product_id") long productId
    );

    @GET(Endpoints.GET_FULL_PRODUCT_BY_CODE)
    Call<ProductDTO> getFullProductByCode(
            @Header("Authorization") String authorization,
            @Path("bar_code") String barCode
    );


    @PUT(Endpoints.PUT_SCANNED_BAR_CODE)
    Call<Void> putScannedCode(
            @Header("Authorization") String authorization,
            @Path("event_id") int eventId,
            @Path("loc_id") int locationId,
            @Body List<Map<String, Object>> listOfCodes
    );





    @GET(Endpoints.GET_FIELDS)
    Call<DataDTO> getFields(
            @Header("Authorization") String authorization
    );




    @PUT(Endpoints.PUT_PRODUCT_UPDATE)
    Call<Void> putUpdatedProduct(
            @Header("Authorization") String authorization,
            @Body Map<String, Object> product
    );


}
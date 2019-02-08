package com.nataliiataran.randomuser;

import com.nataliiataran.randomuser.api.ApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RetrofitInterface {

    @GET("/api/")
    Call<ApiResponse> fetchUsers(@Query("results") int results);
}

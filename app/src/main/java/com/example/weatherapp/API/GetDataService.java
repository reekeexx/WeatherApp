package com.example.weatherapp.API;

import com.example.weatherapp.model.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("data/2.5/weather")
    Call<Root> getRoot(@Query("q") String city,
                       @Query("units") String units,
                       @Query("lang") String lang,
                       @Query("appid") String appid);
}

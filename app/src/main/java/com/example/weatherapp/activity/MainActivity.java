package com.example.weatherapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.weatherapp.API.GetDataService;
import com.example.weatherapp.API.RetrofitClientInstance;
import com.example.weatherapp.R;
import com.example.weatherapp.model.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String CITY = "izhevsk";
    private static final String UNITS = "metric";
    private static final String LANG = "ru";
    private static final String APPID = "d20e797d4f60105e9756d7d6b087a191";

    private TextView temp;
    private TextView description;
    private TextView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = findViewById(R.id.temp);
        description = findViewById(R.id.description);
        city = findViewById(R.id.city);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Root> call = service.getRoot(CITY, UNITS, LANG, APPID);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                temp.setText(String.valueOf((int)response.body().getMain().getTemp()));
                description.setText(response.body().getWeathers().get(0).getDescription());
                city.setText(response.body().getName());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }
}
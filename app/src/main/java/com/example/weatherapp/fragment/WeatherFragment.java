package com.example.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.weatherapp.API.GetDataService;
import com.example.weatherapp.API.RetrofitClientInstance;
import com.example.weatherapp.R;
import com.example.weatherapp.model.Root;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private int pageNumber;

    private String city;
    private String units;
    private String lang;
    private String appid;

    private TextView tempCurrentCity;
    private TextView descriptionCurrentCity;
    private TextView currentCity;

    public static WeatherFragment newInstance(int page) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt("num", page);
        weatherFragment.setArguments(args);
        return weatherFragment;
    }

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments() != null ? getArguments().getInt("num") : 1;

        city = getResources().getString(R.string.city);
        units = getResources().getString(R.string.units);
        lang = getResources().getString(R.string.lang);
        appid = getResources().getString(R.string.appid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result;

        if (pageNumber == 0) {
            result = inflater.inflate(R.layout.fragment_current_weather, container, false);
            createFragmentCurrentCityWeather(result);
        } else {
            result = inflater.inflate(R.layout.fragment_list_cities, container, false);
        }

        return result;
    }

    private void createFragmentCurrentCityWeather(View view) {
        tempCurrentCity = view.findViewById(R.id.city);
        descriptionCurrentCity = view.findViewById(R.id.description);
        currentCity = view.findViewById(R.id.city);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Root> call = service.getRoot(city, units, lang, appid);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                tempCurrentCity.setText(String.valueOf((int) response.body().getMain().getTemp()));
                descriptionCurrentCity.setText(response.body().getWeathers().get(0).getDescription());
                currentCity.setText(response.body().getName());
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }
}

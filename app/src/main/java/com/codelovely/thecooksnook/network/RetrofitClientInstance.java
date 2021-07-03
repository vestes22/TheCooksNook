package com.codelovely.thecooksnook.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static String API_KEY = "1oByUxz5COPxGQzGIQnobSN0TBExqWwT0SmNxoLm";
    private static final String BASE_URL = "https://api.nal.usda.gov/fdc/";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static String getApiKey() { return API_KEY; }
}

package com.codelovely.thecooksnook.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.nal.usda.gov/fdc/";

    public static Retrofit getRetrofitInstance() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(150, TimeUnit.SECONDS);
        client.readTimeout(150, TimeUnit.SECONDS);
        client.writeTimeout(150, TimeUnit.SECONDS);

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client.build())
                    .build();
        }
        return retrofit;
    }

    public static String getApiKey() {
        return "1oByUxz5COPxGQzGIQnobSN0TBExqWwT0SmNxoLm"; }
}

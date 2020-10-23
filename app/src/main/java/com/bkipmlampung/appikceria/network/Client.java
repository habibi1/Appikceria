package com.bkipmlampung.appikceria.network;

import com.bkipmlampung.appikceria.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bkipmlampung.appikceria.BuildConfig.BASE_URL;

public class Client {

    public static Retrofit getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        String BASE_URL = BuildConfig.BASE_URL_APP;

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static Retrofit getClientNotification()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static ApiService getInstanceRetrofit() {
        return getClient().create(ApiService.class);
    }
}

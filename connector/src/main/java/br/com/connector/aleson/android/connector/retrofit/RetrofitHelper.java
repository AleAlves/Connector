package br.com.connector.aleson.android.connector.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleson on 7/1/2018.
 */

public class RetrofitHelper {

    private Retrofit retrofit;

    public RetrofitHelper(String baseUrl) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create(gson));
        this.retrofit = builder.client(httpClient.build()).build();
    }

    public Retrofit instance() {
        return retrofit;
    }

}

package br.com.connector.aleson.android.connector;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.com.connector.aleson.android.connector.api.Test;
import br.com.connector.aleson.android.connector.domain.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //First we init de Connector
        Connector.init("https://sms-token-hub.herokuapp.com/api/");

        Connector.request().create(Test.class).testGet().enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.d("" ,"");
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("" ,"");
            }
        });
    }
}

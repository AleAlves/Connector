package br.com.connector.aleson.android.connector.api;

import br.com.connector.aleson.android.connector.domain.BaseResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Test {

    @GET("v1/version")
    Call<BaseResponse> testGet();

}

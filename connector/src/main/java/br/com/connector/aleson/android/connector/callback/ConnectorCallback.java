package br.com.connector.aleson.android.connector.callback;

import retrofit2.Response;

/**
 * Created by Aleson on 3/3/2018.
 */

public interface ConnectorCallback<T> {

    void onResponse(Response response);

    void onError(Throwable throwable);
}

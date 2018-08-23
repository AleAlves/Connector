package br.com.connector.aleson.android.connector;

import br.com.connector.aleson.android.connector.cryptography.Crypto;
import br.com.connector.aleson.android.connector.parser.Parser;
import br.com.connector.aleson.android.connector.retrofit.RetrofitHelper;

/**
 * Created by Aleson on 8/7/2018.
 */

public class Connector {

    private static Crypto crypto;
    private static RetrofitHelper retrofitHelper;

    public static void init(String baseUrl) {
        crypto = new Crypto();
        retrofitHelper = new RetrofitHelper(baseUrl);
    }

    public static Crypto crypto() {
        return crypto;
    }

    public static RetrofitHelper retrofit() {
        return retrofitHelper;
    }

    public static Parser parser(){
        return new Parser();
    }
}

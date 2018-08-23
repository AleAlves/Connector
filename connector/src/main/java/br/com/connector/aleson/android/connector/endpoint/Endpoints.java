package br.com.connector.aleson.android.connector.endpoint;

import br.com.connector.aleson.android.connector.BuildConfig;

/**
 * Created by Aleson on 2/24/2018.
 */

public class Endpoints {

    private Endpoints() {
        //Constructor to avoid instance
    }

    private static String server = "https://seachando.herokuapp.com/" ;

    public static String getServer() {
        return server;
    }
}

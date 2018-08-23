package br.com.connector.aleson.android.connector.parser;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Aleson on 6/27/2018.
 */

public class Parser {

    public final <T> T getObjectList(String data, Class clazz) {
        return new Gson().fromJson(data, TypeToken.getParameterized(List.class, clazz).getType());
    }

    public <T> T getObject(String data, Class clazz) {
        return new Gson().fromJson(data, (Type) clazz);
    }

}
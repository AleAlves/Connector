package br.com.connector.aleson.android.connector.domain;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {


    @SerializedName("version")
    private String version;

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private int code;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "Version: " + version +
                "Status " + status +
                "code: " + code;
    }
}

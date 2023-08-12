package com.android.demo_app.api.response;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private JsonElement data;

    @SerializedName("message")
    private String message;

    @SerializedName("status_code")
    private int status_code;

    @SerializedName("detail")
    private String details;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JsonElement getData() {
        return data;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setData(JsonElement data) {
        if (!(data instanceof JsonNull))
            this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.android.demo_app.api.service;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.android.demo_app.api.APiInterface;
import com.android.demo_app.api.response.ApiResponse;
import com.android.demo_app.app.BaseApplication;
import com.android.demo_app.app.Constants;


import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainService {
    private static final APiInterface apiService = BaseService.getAPIClient(Constants.API_DEFAULT_URL).create(APiInterface.class);
    private static final APiInterface apiInterfaceDocUpload = BaseService.getAPIClient(Constants.API_DOC_UPLOAD_URL).create(APiInterface.class);
    public static String FAILED_MSG = "Something went wrong!";
    public static String ERROR="Error";

    public MainService() {


    }

    public static ApiResponse customResponse(Response<ApiResponse> response) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus_code(response.code());
        assert response.body() != null;
        apiResponse.setData(response.body().getData());
        apiResponse.setMessage(response.body().getMessage());
        apiResponse.setSuccess(response.body().isSuccess());
        return apiResponse;
    }

    public static ApiResponse noNetworkResponse() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus_code(1000);
        apiResponse.setData(null);
        apiResponse.setMessage("Network Error!!");
        apiResponse.setSuccess(false);
        return apiResponse;
    }

    public static ApiResponse tokenExpiredResponse(String msg, int code) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus_code(code);
        apiResponse.setData(null);
        apiResponse.setMessage(msg);
        apiResponse.setSuccess(false);
        return apiResponse;
    }
}

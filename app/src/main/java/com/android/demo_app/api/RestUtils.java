package com.android.demo_app.api;


import com.android.demo_app.app.Constants;

public class RestUtils {

    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";

    public static String getEndPoint(String urlType) {
        if(urlType.equalsIgnoreCase(Constants.API_DEFAULT_URL)){
            return Constants.API_DEFAULT_URL;
        } else if (urlType.equalsIgnoreCase(Constants.API_DOC_UPLOAD_URL)) {
            return Constants.API_DOC_UPLOAD_URL;
        }
        return Constants.API_DEFAULT_URL;
    }
}

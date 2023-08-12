package com.android.demo_app.app;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static boolean isNetworkDialogOpened = false;
    public static String API_DEFAULT_URL = "default";
    public static String API_DOC_UPLOAD_URL = "api_doc_upload_url";


    public enum collectionType {// Collection types = Promis to pay - PTP, Full Pay - FP, Partial Pay - PP
        PTP, FP, PP
    }

    public enum paymentType {// Payment types = Cash - C, Cheque - CH, NEFT - N, UPI - U, Not Available - NA
        C, CH, U, NA, N
    }

    public enum searchType { // Name - N, Address - A, Code - C, Phone -P
        N, A, C, P
    }

    public static <T> boolean isListEmpty(List<T> list) {
        return list == null || list.size() == 0;
    }

    public static ListAdapter getAdapter(Context mContext, String[] list) {
        return new ArrayAdapter(mContext, android.R.layout.simple_spinner_dropdown_item, list);
    }

    public static <T> List<T> changeArraytoList(T[] array) {
        List<T> tList = new ArrayList<>();
        Collections.addAll(tList, array);
        return tList;
    }

}

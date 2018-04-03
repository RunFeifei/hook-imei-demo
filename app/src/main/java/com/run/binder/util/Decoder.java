package com.run.binder.util;


import android.util.Base64;

/**
 * Created by PengFeifei on 2018/3/12.
 */

public class Decoder {

    public static String decode(String string) {
        return new String(Base64.decode(string, Base64.DEFAULT));
    }

    public static String enCoded(String string) {
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT);
    }
}

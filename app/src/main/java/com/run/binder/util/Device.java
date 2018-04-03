package com.run.binder.util;

/**
 * Created by PengFeifei on 2018/4/3.
 */

import android.provider.Settings;

import com.run.binder.APP;

import org.json.JSONObject;

import java.util.TimeZone;

//Log.e("TAG-->",getUuid());
//Log.e("TAG-->",getManufacturer()+"-"+getModel()+"-"+getOSVersion()+"-"+getSerialNumber());

//{"uuid":"af6b014bd8a1578d","version":"6.0.1","platform":"Android","model":"Redmi Note 3","manufacturer":"Xiaomi","isVirtual":false,"serial":"bf45a547"}
//{"host":"jfxsapp.fawjiefang.com.cn","port":80,"context":"TdsAppService","reqMethod":"post","appKey":"5678901234","local":{"_strategy":{}},
// "method":"BillTest/UserLogin","params":{"vUserName":"A0901030","vPwd":"111111","vPwdType":"01","vDeviceOS":"Android","vIMEI":"af6b014bd8a1578d",
// "vMobileInfo":"Xiaomi-Redmi Note 3-6.0.1-bf45a547","vAppVersion":"1.0.26"}}
public class Device {

    private static final String ANDROID_PLATFORM = "Android";
    private static final String AMAZON_PLATFORM = "amazon-fireos";
    private static final String AMAZON_DEVICE = "Amazon";

    private Device() {
    }


    public String toString() {
        try {
            JSONObject r = new JSONObject();
            r.put("uuid", getUuid());
            r.put("version", this.getOSVersion());
            r.put("platform", this.getPlatform());
            r.put("model", this.getModel());
            r.put("manufacturer", this.getManufacturer());
            r.put("isVirtual", this.isVirtual());
            r.put("serial", this.getSerialNumber());
            return r.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static String getPlatform() {
        String platform;
        if (isAmazonDevice()) {
            platform = AMAZON_PLATFORM;
        } else {
            platform = ANDROID_PLATFORM;
        }
        return platform;
    }

    public static String getUuid() {
        String uuid = Settings.Secure.getString(APP.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return uuid;
    }

    public static String getModel() {
        String model = android.os.Build.MODEL;
        return model;
    }

    private static String getProductName() {
        String productname = android.os.Build.PRODUCT;
        return productname;
    }

    public static String getManufacturer() {
        String manufacturer = android.os.Build.MANUFACTURER;
        return manufacturer;
    }

    public static String getSerialNumber() {
        String serial = android.os.Build.SERIAL;
        return serial;
    }

    public static String getOSVersion() {
        String osversion = android.os.Build.VERSION.RELEASE;
        return osversion;
    }

    private static String getSDKVersion() {
        String sdkversion = android.os.Build.VERSION.SDK;
        return sdkversion;
    }

    private static String getTimeZoneID() {
        TimeZone tz = TimeZone.getDefault();
        return (tz.getID());
    }

    private static boolean isAmazonDevice() {
        if (android.os.Build.MANUFACTURER.equals(AMAZON_DEVICE)) {
            return true;
        }
        return false;
    }

    private static boolean isVirtual() {
        return android.os.Build.FINGERPRINT.contains("generic") ||
                android.os.Build.PRODUCT.contains("sdk");
    }

    public static String getMobileInfo() {
        return getManufacturer() + "#" + getModel() + "#" + getOSVersion() + "#" + getSerialNumber();
    }

    public static String getImei() {
        return getUuid();
    }
}

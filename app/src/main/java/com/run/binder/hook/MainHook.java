package com.run.binder.hook;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.contains("com.faw.jf.dealer.pro")) {
            return;
        }
        final Object activityThread = XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null),
                "currentActivityThread");
        final Context systemContext = (Context) XposedHelpers.callMethod(activityThread, "getSystemContext");
        Uri uri = Uri.parse("content://com.run.binder.data.AppInfoProvider/app");

        Cursor cursor = systemContext.getContentResolver().query(uri, new String[] {"uuid", "manufacturer", "model", "oSVersion", "serialNumber"},
                "package_name=?", new
                String[] {"KEY"}, null);
        if (cursor == null) {
            return;
        }
        String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
        String manufacturer = cursor.getString(cursor.getColumnIndex("manufacturer"));
        String model = cursor.getString(cursor.getColumnIndex("model"));
        String oSVersion = cursor.getString(cursor.getColumnIndex("oSVersion"));
        String serialNumber = cursor.getString(cursor.getColumnIndex("serialNumber"));
        HookUtils.HookAndChange(loadPackageParam.classLoader, uuid, manufacturer, model, oSVersion, serialNumber);
    }

}

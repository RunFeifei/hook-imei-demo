package com.run.binder.hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

class HookUtils {

    static void HookAndChange(ClassLoader classLoader,final String uuid,final String manufacturer,final String model,final String oSVersion,final String serialNumber) {

        XposedHelpers.findAndHookMethod("org.apache.cordova.device.Device", classLoader,
                "getUuid", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(uuid);
                    }
                });
        XposedHelpers.findAndHookMethod("org.apache.cordova.device.Device", classLoader,
                "getManufacturer", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(manufacturer);
                    }
                });
        XposedHelpers.findAndHookMethod("org.apache.cordova.device.Device", classLoader,
                "getModel", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(model);
                    }
                });
        XposedHelpers.findAndHookMethod("org.apache.cordova.device.Device", classLoader,
                "getOSVersion", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(oSVersion);
                    }
                });
        XposedHelpers.findAndHookMethod("org.apache.cordova.device.Device", classLoader,
                "getSerialNumber", new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        param.setResult(serialNumber);
                    }
                });

    }


}

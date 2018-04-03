package com.run.binder.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.run.binder.APP;

/**
 * Created by PengFeifei on 2018/4/3.
 */

public class ClipHelper {

    private static ClipboardManager clipboard = (ClipboardManager) APP.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

    public static void copy(String string) {
        ClipData clipData = ClipData.newPlainText(null, string);
        clipboard.setPrimaryClip(clipData);
    }

    public static String get() {
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            CharSequence text = clipData.getItemAt(0).getText();
            return text.toString();
        }
        return null;
    }
}

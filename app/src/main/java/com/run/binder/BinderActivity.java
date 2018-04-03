package com.run.binder;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.run.binder.data.DbHelper;
import com.run.binder.util.Decoder;
import com.run.binder.util.Device;
import com.run.binder.util.ToastUtil;

public class BinderActivity extends AppCompatActivity implements View.OnClickListener {

    private SQLiteDatabase mSQLiteDatabase;
    private static final String PACAKGE_NAME = "KEY";
    private String uuid = "";
    private String manufacturer = "";
    private String model = "";
    private String oSVersion = "";
    private String serialNumber = "";

    private TextView text0;
    private TextView text1;
    private String data = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        text0 = (TextView) findViewById(R.id.text0);
        text1 = (TextView) findViewById(R.id.text1);
        text0.setOnClickListener(this);
        text1.setOnClickListener(this);
        init();
        data = getIntent().getStringExtra("KEY");
        data = Decoder.decode(data);
        Log.e("TAG-->get-decodedData", data);
    }


    private void init() {
        mSQLiteDatabase = new DbHelper(this).getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.query(DbHelper.APP_TABLE_NAME, new String[] {"uuid,manufacturer,model,oSVersion,serialNumber"},
                    "package_name=?", new String[] {PACAKGE_NAME},
                    null, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null && cursor.moveToNext()) {
            uuid = cursor.getString(cursor.getColumnIndex("uuid"));
            manufacturer = cursor.getString(cursor.getColumnIndex("manufacturer"));
            model = cursor.getString(cursor.getColumnIndex("model"));
            oSVersion = cursor.getString(cursor.getColumnIndex("oSVersion"));
            serialNumber = cursor.getString(cursor.getColumnIndex("serialNumber"));
            cursor.close();
        }
        log(true);
    }

    private void log(boolean isBefore) {
        String s = isBefore ? "-->before-->  " : "-->after-->  ";
        Log.e("TAG-->hack" + s, uuid);
        Log.e("TAG-->hack" + s, manufacturer + "-" + model + "-" + oSVersion + "-" + serialNumber);
    }

    @Override
    public void onClick(View v) {
        if (v == text0) {
            doChange(Device.getUuid(), Device.getManufacturer(), Device.getModel(), Device.getOSVersion(), Device.getSerialNumber());
        }
        if (v == text1) {
            doHack();
        }
    }

    private void doHack() {
        if (TextUtils.isEmpty(data)) {
            ToastUtil.show(this, "未知错误!!!!");
            return;
        }
        String[] arr = data.split("#");
        if (arr == null || arr.length != 5) {
            ToastUtil.show(this, "未知错误!!!!");
            return;
        }
        uuid = arr[0];
        manufacturer = arr[1];
        model = arr[2];
        oSVersion = arr[3];
        serialNumber = arr[4];
        doChange(uuid, manufacturer, model, oSVersion, serialNumber);
    }

    private void doChange(String uuid, String manufacturer, String model, String oSVersion, String serialNumber) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("package_name", "KEY");
        contentValues.put("uuid", uuid);
        contentValues.put("manufacturer", manufacturer);
        contentValues.put("model", model);
        contentValues.put("oSVersion", oSVersion);
        contentValues.put("serialNumber", serialNumber);

        mSQLiteDatabase.insertWithOnConflict(DbHelper.APP_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        mSQLiteDatabase.close();
        log(false);
        setResult(RESULT_OK);
        finish();
    }
}

package com.run.binder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.run.binder.util.ClipHelper;
import com.run.binder.util.Decoder;
import com.run.binder.util.Device;
import com.run.binder.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private TextView textGo;
    private TextView textShow;
    private TextView textGet;
    private TextView edtGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgress(0);
        mProgressDialog.setMax(100);
        mProgressDialog.setMessage("请稍等片刻,正在破解中");
        textGo = (TextView) findViewById(R.id.textGo);
        textShow = (TextView) findViewById(R.id.textShow);
        textGet = (TextView) findViewById(R.id.textGet);
        edtGet = (TextView) findViewById(R.id.edtGet);
        textGo.setOnClickListener(this);
        textShow.setOnClickListener(this);
        textGet.setOnClickListener(this);
        textShow.setText(Decoder.enCoded(Device.getImei() + "#" + Device.getMobileInfo()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtGet.setText(ClipHelper.get());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            doWait();
        }

    }

    private void doWait() {
        mProgressDialog.setProgress(0);
        mProgressDialog.show();
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressDialog.dismiss();
                ToastUtil.show(MainActivity.this, "破解完成,请登录");
            }
        }, 5000);
    }

    @Override
    public void onClick(View v) {
        if (v == textShow) {
            if (TextUtils.isEmpty(textShow.getText().toString())) {
                ToastUtil.show(MainActivity.this, "错误!!!!!");
                return;
            }
            ClipHelper.copy(textShow.getText().toString());
            ToastUtil.show(MainActivity.this, "已经复制到粘贴板,去微信发送");
            return;
        }
        if (v == textGo) {
            String string = edtGet.getText().toString();
            if (TextUtils.isEmpty(string)) {
                ToastUtil.show(MainActivity.this, "错误!!!!!");
                return;
            }
            Intent intent = new Intent(this, BinderActivity.class);
            intent.putExtra("KEY", string);
            startActivityForResult(intent, 1);
            return;
        }
        if (v == textGet) {
            edtGet.setText(ClipHelper.get());
        }

    }
}

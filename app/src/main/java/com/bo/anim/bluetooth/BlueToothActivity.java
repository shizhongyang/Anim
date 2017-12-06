package com.bo.anim.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bo.anim.R;

public class BlueToothActivity extends AppCompatActivity implements View.OnClickListener{

    /** Called when the activity is first created. */
    private Button autopairbtn=null;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_tooth);

        autopairbtn=(Button) findViewById(R.id.button1);
        autopairbtn.setOnClickListener(this);
    }

    //设置按钮的监听方法
    @Override
    public void onClick(View v) {
        if (!bluetoothAdapter.isEnabled())
        {
            bluetoothAdapter.enable();//异步的，不会等待结果，直接返回。
        }else{
            bluetoothAdapter.startDiscovery();
        }
    }
}

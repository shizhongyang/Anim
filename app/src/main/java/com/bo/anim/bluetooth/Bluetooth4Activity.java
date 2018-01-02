package com.bo.anim.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bo.anim.R;
import com.bo.anim.util.PermissionUtil;

import java.util.List;

public class Bluetooth4Activity extends AppCompatActivity implements BleUtil.BTUtilListener{

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth4);
        mHandler = new Handler();

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "不支持", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "不支持", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        call();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    protected void onResume() {
        super.onResume();

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }
       // scanLeDevice(true);
        BleUtil instance = BleUtil.getInstance();
        instance.setContext(getApplicationContext());
        instance.setBTUtilListener(this);
        instance.scanLeDevice(true);

    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }


    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.i("DeviceScan", "run: " + device.getName());
                        }
                    });
                }
            };

    @Override
    public void onLeScanStart() {

    }

    @Override
    public void onLeScanStop() {

    }

    @Override
    public void onLeScanDevices(List<BluetoothDevice> listDevice) {
        for (BluetoothDevice bluetoothDevice : listDevice) {
            System.out.println("-----"+bluetoothDevice.getName());
        }
    }

    @Override
    public void onConnected(BluetoothDevice mCurDevice) {

    }

    @Override
    public void onDisConnected(BluetoothDevice mCurDevice) {

    }

    @Override
    public void onConnecting(BluetoothDevice mCurDevice) {

    }

    @Override
    public void onDisConnecting(BluetoothDevice mCurDevice) {

    }

    @Override
    public void onStrength(int strength) {

    }

    @Override
    public void onModel(int model) {

    }
    int REQUEST_PERMISSION_CALL = 100;
    String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    String STAG = "Permission";
    //开启权限
    private void  call() {
        if (PermissionUtil.checkPermission(ACCESS_COARSE_LOCATION,getApplicationContext())) {
        } else {
            PermissionUtil.startRequestPermision(ACCESS_COARSE_LOCATION,this);
        }
    }
}

package com.example.gott;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    MyReceiver broadcastReceiver;
    private Button hotspotenable, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // the broadcast receiver stuff
        registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BOOT_COMPLETED));

        Button checkbtn;

        checkbtn = findViewById(R.id.checknewtwork_btn);
        hotspotenable = findViewById(R.id.bluetooth_btn);
        start = findViewById(R.id.btn);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.System.canWrite(this.getApplicationContext())) {

            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        hotspotenable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewWifiApNetwork();
            }
        });

        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNetworkAvailable(MainActivity.this);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JsonStuff.class));
            }
        });
    }

    public void CreateNewWifiApNetwork() {
        ApManager ap = new ApManager(
                this.getApplicationContext());
        if(ap.isApOn())
            hotspotenable.setText(R.string.on);
        else
            hotspotenable.setText(R.string.off);
        ap.createNewNetwork("mikael","");
    }


    /**
     * If network connectivity is available, will return true
     *
     * @param context the current context
     * @return boolean true if a network connection is available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            Log.d("NetworkCheck", "isNetworkAvailable: No");
            Toast.makeText(context, "NO NETWORK CONNECTION", Toast.LENGTH_SHORT).show();
            return false;
        }

        // get network info for all of the data interfaces (e.g. WiFi, 3G, LTE, etc.)
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        // make sure that there is at least one interface to test against
        if (info != null) {
            // iterate through the interfaces
            for (int i = 0; i < info.length; i++) {
                // check this interface for a connected state
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    Log.d("NetworkCheck", "isNetworkAvailable: Yes");
                    Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
                    return true;
                }else{
                //  Toast.makeText(context, "NO INTERNET DWAG ! ", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
}

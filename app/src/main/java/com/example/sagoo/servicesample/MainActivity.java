package com.example.sagoo.servicesample;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private TextView value;
    private Button startService,stopService,bindService,unbindService,getRandomNumber;
    private Intent serviceIntent;
    private boolean isServiceBounded;
    private ServiceConnection serviceConnection;
    private MyService myService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        value = findViewById(R.id.textView);
        startService = findViewById(R.id.start_service);
        stopService = findViewById(R.id.stop_service);
        bindService = findViewById(R.id.bind_service);
        unbindService = findViewById(R.id.unbince_service);
        getRandomNumber = findViewById(R.id.get_random_number);
        serviceIntent = new Intent(this,MyService.class);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onclick","pressed");
                startServiceMethod();
            }
        });
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopServiceMethod();
            }
        });
        bindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindServiceMethod();
            }
        });
        unbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindServiceMethod();
            }
        });
        getRandomNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomNumberMethod();
            }
        });
    }

    private void startServiceMethod() {
        Log.d("onclick","startServiceMethod");
        startService(serviceIntent);
    }

    private void stopServiceMethod() {
        stopService(serviceIntent);
    }

    private void bindServiceMethod() {
        if(serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder) binder;
                    myService = myServiceBinder.getMyservice();
                    isServiceBounded = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBounded = false;
                }
            };
        }
        bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    private void unbindServiceMethod() {
        if(isServiceBounded){
            unbindService(serviceConnection);
            isServiceBounded = false;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getRandomNumberMethod(){
        if(isServiceBounded){
//            value.setText(myService.getRendomNumber());
            int number = myService.getRendomNumber();
            Log.d("number", String.valueOf(number));
            value.setText(String.valueOf(number));
        }
        else {
            value.setText("service is not bound");
        }


    }
}

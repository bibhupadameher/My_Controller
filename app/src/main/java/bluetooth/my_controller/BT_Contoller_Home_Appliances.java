package bluetooth.my_controller;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;


public class BT_Contoller_Home_Appliances extends ActionBarActivity {

    Button b1,b2;
    ImageButton im1,im2;

    TextView lumn,t1;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    int a,b,c;
    String s;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);


        setContentView(R.layout.activity_led_control);
        a = b = c = 0;
        s = "";

        b1 = (Button) findViewById(R.id.button4);
        b2 = (Button) findViewById(R.id.button3);
        im1 = (ImageButton) findViewById(R.id.imageButton);
        im2 = (ImageButton) findViewById(R.id.imageButton2);
        t1 = (TextView) findViewById(R.id.textView2);
        t1.setText("Project By: Sanjay " + "\n" + "App By :Bibhu");
        lumn = (TextView) findViewById(R.id.lumn);

        im1.setImageResource(R.drawable.d);
        im2.setImageResource(R.drawable.c);


        new ConnectBT().execute();

        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++a;
                one();


            }
        });

        im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++b;
                two();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++c;
                both();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {

                ++c;
                both();

            }
        });
    }
        @Override
        public void onResume() {
        super.onResume();

        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

        @Override
        public void onPause() {

        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }


    private void Disconnect()
    {
        if (btSocket!=null)
        {
            try
            {

                btSocket.close();
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish();

    }

    private void one()
    {
        if (btSocket!=null)
        {
            try
            {
                if(a%2==0){
                    s="4";
                    im1.setImageResource(R.drawable.d);

                }else{
                  s="1";
                    im1.setImageResource(R.drawable.f);
                }

                btSocket.getOutputStream().write(s.toString().getBytes());

            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    private void both() {
        if (btSocket != null) {
            try {
                if (c % 2 == 0) {
                    s = "6";
                    im1.setImageResource(R.drawable.d);
                    im2.setImageResource(R.drawable.c);

                } else {
                    s = "3";
                    im1.setImageResource(R.drawable.f);
                    im2.setImageResource(R.drawable.e);
                }
                btSocket.getOutputStream().write(s.toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }
    private void two()
    {
        if (btSocket!=null)
        {
            try
            {
                if(b%2==0){
                    s="5";
                    im2.setImageResource(R.drawable.c);

                }else{
                    s="2";
                    im2.setImageResource(R.drawable.e);
                }
                btSocket.getOutputStream().write(s.toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }


    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_led_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>
    {
        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(BT_Contoller_Home_Appliances.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                 myBluetooth = BluetoothAdapter.getDefaultAdapter();
                 BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                 btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                 BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                 btSocket.connect();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }
}

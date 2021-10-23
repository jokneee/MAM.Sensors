package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SensorManager manager = null;
    private Observator observator = null;

    private Sensor magnetic = null;
    private Sensor gravity = null;
    private Sensor acceleration = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);


        gravity = manager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        magnetic = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        acceleration = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        observator = new Observator((TextView)findViewById(R.id.accTextView),
                (TextView)findViewById(R.id.oriTextView),
                (TextView)findViewById(R.id.angleTextView),
                (ImageView)findViewById(R.id.compassView));
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerSensor(acceleration);
        registerSensor(gravity);
        registerSensor(magnetic);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(observator);
    }

    private void registerSensor(Sensor sensor) {
        if (sensor != null) {
            manager.registerListener(observator, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }
}
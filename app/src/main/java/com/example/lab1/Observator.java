package com.example.lab1;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Surface;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

public class Observator implements SensorEventListener {
    private float[] magnet = null;
    public float[] acceleration = null;

    private TextView accTextView;
    private TextView oriTextView;
    private TextView angleTextView;
    private ImageView compassView;

    public Observator(TextView accTextView, TextView oriTextView, TextView angleTextView, ImageView compassView) {

        this.accTextView = accTextView;
        this.oriTextView = oriTextView;
        this.angleTextView = angleTextView;
        this.compassView = compassView;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                acceleration = event.values.clone();
                accTextView.setText(Arrays.toString(acceleration));
                onOrientationChanged();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnet = event.values.clone();
                onOrientationChanged();
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onOrientationChanged() {
        if (acceleration != null && magnet != null) {
            float[] R = new float[9];
            SensorManager.getRotationMatrix(R, null, acceleration, magnet);
            float[] orientation = new float[3];
            SensorManager.getOrientation(R, orientation);
            oriTextView.setText(Arrays.toString(orientation));
            float azimuth = (float) Math.toDegrees(orientation[0]);
            azimuth = (azimuth + 360) % 360;
            angleTextView.setText(Float.toString(azimuth));
            compassView.setRotation((-1) * azimuth);
        }
    }
}
package com.elitedemoworkspace.activity;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.hardware.Sensor;
import com.elitedemoworkspace.R;

import java.util.List;

public class SensorsActivity extends AppCompatActivity implements SensorEventListener {
    String accelData = "Accelerometer Data";
    String compassData = "Compass Data";
    String gyroData = "Gyro Data";
    private TextView mtv_accelerometer, mtv_magnetic_field, mtv_gyroscope;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        mtv_accelerometer = (TextView) findViewById(R.id.tv_accelerometer);
        mtv_magnetic_field = (TextView) findViewById(R.id.tv_magnetic_field);
        mtv_gyroscope = (TextView) findViewById(R.id.tv_gyroscope);

        SensorManager sensors = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        sensors.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensors.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensors.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);


        List<Sensor> sensor_list= sensors.getSensorList(Sensor.TYPE_ALL);
        TextView tv_sensorlist = (TextView) findViewById(R.id.tv_sensorlist);

        for (int i = 0; i < sensor_list.size(); i++) {
            tv_sensorlist.append("\n" + sensor_list.get(i).getName() + " | " + sensor_list.get(i).getVendor() + " | " + sensor_list.get(i).getVersion());
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        StringBuilder msg = new StringBuilder(event.sensor.getName()).append(" ");
        for (float value : event.values) {
            msg.append("[").append(value).append("]");
        }

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                accelData = msg.toString();
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroData = msg.toString();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                compassData = msg.toString();
                break;
        }
        mtv_accelerometer.setText(accelData);
        mtv_magnetic_field.setText(compassData);
        mtv_gyroscope.setText(gyroData);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

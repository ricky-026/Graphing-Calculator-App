package lab0_203_28.uwaterloo.ca.lab2_203_28;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import ca.uwaterloo.sensortoy.LineGraphView;


public class Lab2_203_28 extends AppCompatActivity {




    LinearLayout l1;

    LineGraphView graph;
    TextView tvAccelerometer;

    Button myButton; ///MUST BE KEPT WORKING AT ALL TIME
    Button reset;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab2_203_28);


        //this is the linearLayout
        l1 = (LinearLayout) findViewById(R.id.automate);
        l1.setOrientation(LinearLayout.VERTICAL);


        // graphs
        graph = new LineGraphView (getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
        l1.addView(graph);
        graph.setVisibility(View.VISIBLE);

        //TextView for reporting Accelerometer Reading
        tvAccelerometer = new TextView(getApplicationContext());
        tvAccelerometer.setText("Accelerometer Instantaneous Readings");
        tvAccelerometer.setTextColor(Color.WHITE);
        l1.addView(tvAccelerometer);

        //Views will be added later


        //Register only the Gravity-Compensated Accelerometer Readings
        final AccelerometerEventListener accListener = new AccelerometerEventListener(tvAccelerometer, graph);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(accListener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);


        myButton = new Button (getApplicationContext());
        myButton.setText("Create Data File");
        myButton.setTextColor(Color.BLACK);
        myButton.setBackgroundColor(Color.WHITE);

        reset = new Button (getApplicationContext());
        reset.setText("Reset Current High");
        myButton.setTextColor(Color.BLACK);
        myButton.setBackgroundColor(Color.WHITE);

        myButton.setOnClickListener(new View.OnClickListener(){
            @Override


            public void onClick(View v) {


                File file = null;
                PrintWriter printWriter = null;

                float [][] historyReading = accListener.getHistoryReading();
                try {
                    file = new File(getExternalFilesDir("Recored Readings"), "AccRecord.csv");
                    printWriter = new PrintWriter(file);

                    for (int a = 0; a< historyReading.length; a++){

                        printWriter.println(String.format("%.2f, %.2f, %.2f",
                                historyReading[a][0], historyReading[a][1], historyReading[a][2]));

                    }

                    Log.d("ECE 155 Lab: ", "Accelerometer Record Written in" + file);


                } catch (IOException io){
                    Log.e("Exception", "File write failed: " + file.toString());
                }
                finally {

                    printWriter.flush();
                    printWriter.close();
                }
            }
        });


        //Adding the views

        l1.addView(myButton);
        l1.addView (reset);
    }




}







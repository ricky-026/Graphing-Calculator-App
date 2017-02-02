package lab0_203_28.uwaterloo.ca.lab2_203_28;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import ca.uwaterloo.sensortoy.LineGraphView;

/**
 * Created by Hao Zhe Yue on 2017-02-01.
 */

public class AccelerometerEventListener implements SensorEventListener {


    private final float FILTER_C = 10.0f;

    //FSM: setup FSM states and Signatures here
    //FSM: setup threshold constants here
    //FSM: Setup FSM sample counter here


    private TextView instanceOutput;
    private LineGraphView graphView;


    private float [][] historyReading = new float [100][3];


    private void insertReading (float [] values){



        for (int a = 1; a < 100; a++){

            historyReading[a-1][0] =  historyReading[a][0];
            historyReading[a-1][1] =  historyReading[a][1];
            historyReading[a-1][2] =  historyReading[a][2];
        }


        historyReading[99][0] += (values[0] - historyReading[99][0])/FILTER_C;
        historyReading[99][1] += (values[1] - historyReading[99][1])/FILTER_C;
        historyReading[99][2] += (values[2] - historyReading[99][2])/FILTER_C;
    }

    //FSM1: Implement FSM method here

    private void myFSM (float input){

    }

    //FSM2L Implement FSM gesture computation mehtod here

    //FSM3: Implement a FSM reset method here


    public AccelerometerEventListener(TextView instanceOutput, LineGraphView graphView){

        this.instanceOutput = instanceOutput;
        this.graphView = graphView;

    }

    public float [][] getHistoryReading(){


        return historyReading;
    }

    @Override
    public void onSensorChanged(SensorEvent se) {


        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
            //insert the new values into the FIFO buffer
            //This method will apply the Low Pass Filter to the incoming values


            insertReading(se.values);


            instanceOutput.setText("The Accelerometer Reading is: \n" + String.format("(%.2f, %.2f, %.2f)",
                    se.values[0], se.values[1], se.values[2]));
        }


        //Add the points to graph
        //Add the filtered Reading to Graph

        graphView.addPoint(historyReading[99]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

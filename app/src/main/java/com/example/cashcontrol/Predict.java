package com.example.cashcontrol;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.cashcontrol.HistoryShow.string_history;

public class Predict extends AppCompatActivity {
    private static final String TAG = "Data";

    //public static ArrayList<String> predict_data  = new ArrayList<>();
    public static ArrayList food  = new ArrayList<>();
    public static ArrayList travel  = new ArrayList<>();
    public static ArrayList others  = new ArrayList<>();
    public static int f;
    public static int t;
    public static int o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        //  predict_data = string_history;
        Log.i(TAG, "food" + food.toString());
        Log.i(TAG, "travel" + travel.toString());
        Log.i(TAG, "others" + others.toString());
        f = (int) LinearRegression(food);
        t = (int) LinearRegression(travel);
        o = (int) LinearRegression(others);
        Log.i(TAG, "f" + Float.toString(f));
        Log.i(TAG, "o" + Float.toString(o));
        Log.i(TAG, "t" + Float.toString(t));
        TextView tv = (TextView) findViewById(R.id.textView7);
        if (f != 0)
            tv.setText("We predict that your next food purchase will be of Rs " + f);
        else
            tv.setText("We cannot predict your next food purchase");
        TextView tv1 = (TextView) findViewById(R.id.textView15);
        if (t != 0)
            tv1.setText("We predict that your next travel purchase will be of Rs " + t);
        else
            tv1.setText("We cannot predict your next travel purchase");
        TextView tv2 = (TextView) findViewById(R.id.textView16);
        if (o != 0)
            tv2.setText("We predict that your next miscellaneous purchase will be of Rs " + o);
        else
            tv2.setText("We cannot predict your next miscellaneous purchase");
    }


    public float LinearRegression(ArrayList temp){
        int size = temp.size();
        int x[] = new int[size];
        for(int i = 0;i < size;i++)
            x[i] = i + 1;


        int sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for(int i = 0;i < size;i++){
            sumX = sumX + x[i];
            sumY = sumY + (int)temp.get(i);
            sumXY = sumXY + (x[i]*(int)(temp.get(i)));
            sumX2 = sumX2 + (x[i]*x[i]);
        }

        float m = (float)((size*sumXY) - (sumX*sumY)) / ((size*sumX2) - (sumX*sumX));
        float c = (sumY - m*sumX)/size;

        float predicted_value = m*(size + 1) + c;
        return predicted_value;
    }
}


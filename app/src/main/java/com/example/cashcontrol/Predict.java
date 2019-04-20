package com.example.cashcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

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
        Log.i(TAG,"food" + food.toString());
        Log.i(TAG,"travel" + travel.toString());
        Log.i(TAG,"others" + others.toString());
         f = (int)LinearRegression(food);
         t = (int)LinearRegression(travel);
         o = (int)LinearRegression(others);
        Log.i(TAG,"f" + Float.toString(f));
        Log.i(TAG,"o" + Float.toString(o));
        Log.i(TAG,"t" + Float.toString(t));
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


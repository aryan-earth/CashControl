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
    public static ArrayList<Integer> food  = new ArrayList<>();
    public static ArrayList<Integer> travel  = new ArrayList<>();
    public static ArrayList<Integer> others  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
      //  predict_data = string_history;
        Log.i(TAG,"food" + food.toString());
        Log.i(TAG,"travel" + travel.toString());
        Log.i(TAG,"others" + others.toString());


    }
}

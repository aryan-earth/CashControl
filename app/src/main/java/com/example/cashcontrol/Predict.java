package com.example.cashcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class Predict extends AppCompatActivity {
    private static final String TAG = "Data";

    //public static ArrayList<String> predict_data  = new ArrayList<>();
    public static ArrayList<Integer> food  = new ArrayList<>();
    public static ArrayList<Integer> travel  = new ArrayList<>();
    public static ArrayList<Integer> others  = new ArrayList<>();
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
        int number_of_pairs = (size*(size-1))/2;
        float slopes[] = new float[number_of_pairs];
        for(int i = 0;i < size;i++)
            x[i] = i + 1;

        int sumX = 0, sumY = 0, sumXY =0, sumX2=0;

        /*for(int i = 0;i < size;i++){
            sumX = sumX + x[i];
            sumY = sumY + (int)temp.get(i);
        }*/

        int count = 0;
        int j_counter = 1;

        for(int i = 0; i < size; i++)
        {
            for(int j = j_counter; j < size; j++)
            {
              slopes[count] = (float)((int)temp.get(j)-(int)temp.get(i))/(j-1);
              count++;
            }
            j_counter++;
        }




        for(int i = 0;i < size;i++){
            sumX = sumX + x[i];
            sumY = sumY + (int)temp.get(i);
            //sumXY = sumXY + (x[i]*(int)(temp.get(i)));
            //sumX2 = sumX2 + (x[i]*x[i]);
        }

        //float m = (float)((size*sumXY) - (sumX*sumY)) / ((size*sumX2) - (sumX*sumX));



            //Insertion Sort
            for(int i = 1; i < number_of_pairs; ++i) {
                float key = slopes[i];
                int j = i - 1;

                while (j >= 0 && slopes[j] > key) {
                    slopes[j + 1] = slopes[j];
                    j = j - 1;
                }
                slopes[j + 1] = key;
            }
            float m;

            if(number_of_pairs%2 == 0) {
                float a = slopes[(number_of_pairs/2) - 1] / 2;
                float b = slopes[(number_of_pairs/ 2)] / 2;
                m = a + b;
            }
            else
                m = slopes[(number_of_pairs - 1)/2];

        //float c = (sumY - m*sumX)/size;

        float[] intercepts = new float[size];
        for(int i = 0; i  < size; i++)
            intercepts[i] = (int)temp.get(i) - m*x[i];

        float c;

        for(int i = 1; i < size; ++i) {
            float key = intercepts[i];
            int j = i - 1;

            while (j >= 0 && intercepts[j] > key) {
                intercepts[j + 1] = intercepts[j];
                j = j - 1;
            }
            intercepts[j + 1] = key;
        }

        if(size%2 == 0) {
            float a = intercepts[(size/ 2)-1] / 2;
            float b = intercepts[(size/ 2)] / 2;
            c = a + b;
        }
        else
            c = intercepts[(size -1)/2];

        float predicted_value = m*(size + 1) + c;
        return predicted_value;
    }

    public void showGraphFood(View view) {
        Button graph_button_food  = (Button)findViewById(R.id.button_food_graph);
        Intent intent = new Intent(this, GraphFood.class);
        startActivity(intent);
    }

    public void showGraphTravel(View view) {
        Button graph_button_travel  = (Button)findViewById(R.id.button_graph_travel);
        Intent intent = new Intent(this, GraphTravel.class);
        startActivity(intent);
    }

    public void showGraphOthers(View view) {
        Button graph_button_others  = (Button)findViewById(R.id.button_others_graph);
        Intent intent = new Intent(this, GraphOthers.class);
        startActivity(intent);
    }
}


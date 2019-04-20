package com.example.cashcontrol;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import static com.example.cashcontrol.MainActivity.st;

public class HistoryShow extends AppCompatActivity {
    private static final  String TAG = "HistoryShow";
    public static int count;
    public static ArrayList<String> string_history = new ArrayList<>();

        //a list to store all the products
    List<History> historyList;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "in OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //Now store the string cash
        //getting the recyclerview from xml
        recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initializing the product list
        historyList = new ArrayList<>();
        //adding items to our list
        try  {
                int n = string_history.size();
                for(int i=0; i<n;i++) {
                    String s1 = string_history.get(i);

                    ((ArrayList)historyList).add(new History(i,s1));
                }
        } catch(ArrayIndexOutOfBoundsException exc) {
            //Out of bounds
        }
        //creating a recyclerview adapter
        HistoryAdapter adapter = new HistoryAdapter(this, historyList);
        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
    public static void saveTrans(String s) {
        //Save the string as soon as it gets changed in MainActivity.class
        string_history.add(st);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"in onResume");
        //loadArray(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"in onPause");
        //saveArray();
    }
}
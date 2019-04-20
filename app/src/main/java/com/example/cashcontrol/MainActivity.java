package com.example.cashcontrol;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.cashcontrol.HistoryShow.count;
import static com.example.cashcontrol.HistoryShow.saveTrans;


public class MainActivity extends AppCompatActivity {
    private static final  String TAG = "HistoryShow";
    SeekBar seekbar;
    TextView mspent;
    TextView wCash;
    String walletCash;  //this variable  stores the current wallet balance

    int manualProgress;
    public static String st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "main:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        mspent = (TextView)findViewById(R.id.enterManuallyTextView);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Change the textView according to progress

                if(fromUser == true){
                    seekBarText(progress);
                }

            }



            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Enter manually listener
        EditText enterCost = findViewById(R.id.enterManuallyTextView);
        enterCost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Here, when user inputs manually the textView should  not change
                //when the seekbar position changes
                int i;
                try{
                    i = Integer.parseInt(s.toString());  //get the  amount entered manually
                } catch(NumberFormatException exc) {
                    i = 0;
                }
                int p = enterManually(i);
                seekbar.setProgress(p);//this is changing the position of the seekbar but it is also changing the textView which i don't want

                st = s.toString();
            }
        });


        //Open registration activity for first run :source  internet
        Boolean isFirstRun = getSharedPreferences("PREFERENCE" , MODE_PRIVATE).getBoolean("isFirstRun" , true);

        if(isFirstRun) {
            //show registration activity
            startActivity(new Intent(MainActivity.this , Registration.class));
        }

        getSharedPreferences("PREFERENCE" , MODE_PRIVATE).edit().putBoolean("isFirstRun" , false).commit();

            //Get user details from first run
            String userName = getIntent().getStringExtra("name");
            walletCash = getIntent().getStringExtra("cash");//till here everything works

           if(isFirstRun) {
               wCash = (TextView)findViewById(R.id.wallet_cash);
               wCash.setText(walletCash);
           }

    }


     public int enterManually(int i) {
        if(i >0 && i<=10){
            manualProgress =  0;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }
        else if(i >10 && i <= 20){
            manualProgress = 1;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }

        else if(i >20 && i<=30){
            manualProgress = 2;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }

        else if(i>30 && i<=40){
            manualProgress = 3;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }

        else if(i>40 && i<=50){
            manualProgress =  4;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }

        else if(i>50 && i<=60){
            manualProgress  = 5;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }

        else if(i>60 && i<=100){
            manualProgress = 6;
            if(i%10 != 0)
                manualProgress  = manualProgress -1;
        }
            return manualProgress;
    }

    public void seekBarText(int p) {
        switch(p) {
            case 0:
                mspent.setText("10");
                st = "10";
                return;
            case 1:
                mspent.setText("20");
                st = "20";
                return;
            case 2:
                mspent.setText("30");;
                st = "30";
                return;
            case 3:
                mspent.setText("40");
                st ="40";
                return;
            case 4:
                mspent.setText("50");
                st = "50";
                return;
            case 5:
                mspent.setText("60");
                st = "60";
                return;
            case 6:
                mspent.setText("100");
                st = "100";
                return;
            default:
                return;
        }

    }

    //This method updates wallet cash

    //TODO:Add transaction history in <ArrayList>  in HistoryShow.class
    public void updateCash(View view) {
        //Here the money selected by the user to be spent is deducted from his current cash amount.

        try {
            int x = Integer.parseInt(walletCash);
            int y = Integer.parseInt(st);
            int z = x - y;    //Deduct money spent
            walletCash= Integer.toString(z);  //This line converts integer to String
            wCash.setText(walletCash);
        } catch(NumberFormatException exc) {
            st  = "0";
        }
        wCash = (TextView)findViewById(R.id.wallet_cash);
        count++;
        saveTrans(st);
    }

    public void openPredictions(View view) {
        //open predictions activity
        Intent intent  = new Intent(MainActivity.this, Predict.class);
        startActivity(intent);
    }


    public void history(View view) {
        Intent intent  = new Intent(MainActivity.this, HistoryShow.class );
        //TODO: Send the money spent by the  user to HistoryShow class for storage.Use intent to do so
        intent.putExtra("money" ,  st);
        intent.putExtra("c" ,count);
        startActivity(intent);
//        Toast toast = Toast.makeText(this, Integer.toString(count),Toast.LENGTH_LONG);
//        toast.show();
        Log.i(TAG, "main:launch History");
    }



    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        Log.i(TAG, "main:onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        Log.i(TAG, "main:onResume");
    }
/*
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"main:onStop");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"main:onStart");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"main:onDestroy");
    }*/

    public void loadData() {
        //get preferences
        SharedPreferences prefs= this.getSharedPreferences("walletKey" , MainActivity.MODE_PRIVATE);
        walletCash = prefs.getString("wallet",walletCash);
        count = prefs.getInt("count",count);
        wCash = (TextView)findViewById(R.id.wallet_cash);
        wCash.setText(walletCash);
    }

    public void saveData() {
        //Save wallet status
        SharedPreferences prefs = this.getSharedPreferences("walletKey" , MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("count",count);
        editor.putString("wallet" , walletCash);
        editor.commit();
    }

}

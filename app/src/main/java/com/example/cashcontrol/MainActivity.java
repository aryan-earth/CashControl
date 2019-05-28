package com.example.cashcontrol;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.cashcontrol.HistoryShow.count;
import static com.example.cashcontrol.HistoryShow.saveTrans;
import static com.example.cashcontrol.Predict.food;
import static com.example.cashcontrol.Predict.others;
import static com.example.cashcontrol.Predict.travel;


public class MainActivity extends AppCompatActivity implements LocationListener {
    Context context = this;
    double home_lat = 30.563301;  //home
    double home_lon = 76.896055; //coordinates
    int flag = 0;
    ImageButton addButton;
    AlertDialog builder;
    public TextView aText;

   //Coordinates of end points of hostel
    public static double lat_BL = 30.4169775;
    public static double lon_BL = 77.9669465;
    public static double lat_BR = 30.4172316;
    public static double lon_BR = 77.9670384;
    public static double lat_FL = 30.4171330;
    public static double lon_FL = 77.9665147;
    public static double lat_FR = 30.4173536;
    public static double lon_FR = 77.9666096;
    //Current coordinates are in lat and lon
    public static double curr_lat;
    public static double curr_lon;
    LocationManager locationManager;
    private static final  String TAG = "HistoryShow";
    SeekBar seekbar;
    TextView mspent;
    TextView wCash;
    String walletCash;  //this variable  stores the current wallet balance

    int manualProgress;
    public static String st;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "main:onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekbar = findViewById(R.id.seekBar);
        mspent = findViewById(R.id.enterManuallyTextView);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Change the textView according to progress

                if (fromUser == true) {
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
        //============================================
        Log.i(TAG,"Before get locatioon");
        getLocation();
        //TODO:Show notification if user is at home

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
               wCash = findViewById(R.id.wallet_cash);
               wCash.setText(walletCash);
           }


        Log.i(TAG,"Exiting onCreate");
    }


    void getLocation() {
        Log.i(TAG,"In get location");
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
        Log.i(TAG,"Exiting getLocation");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void set(double a, double b) {
        curr_lat = a;
        curr_lon = b;
        Log.i(TAG,  "Coordinates" + curr_lat + " "+ curr_lon);
        //run this block after each time

        //Check for location in time intervals
        //Declare the timer
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {
                                      //call a method
                                      repeat();
                                  }},0,1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void repeat() {
        Log.i(TAG, "In repeat");
        if(true) {
            Log.i(TAG,"TRUE");
            //send notification
            addNotification();
        }
        else{
            Log.i(TAG,"FALSE");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG,"In onLocationChanged");
        curr_lat = location.getLatitude();
        curr_lon = location.getLongitude();
        Toast.makeText(this, Double.toString(curr_lat), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, Double.toString(curr_lon), Toast.LENGTH_SHORT).show();
        set(curr_lat,curr_lon);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        //Toast.makeText(this, "Thanks brah!", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"12345")
                .setSmallIcon(R.drawable.ic_add) //set icon for notification
                .setContentTitle("Welcome home!") //set title of notification
                .setContentText("Did you spend any cash?")//this is notification message
                .setAutoCancel(true) // makes auto cancel of notification
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); //set priority of notification

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        CharSequence name = "n";
        String description = "d";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("12345", name, importance);
        channel.setDescription(description);
        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
        manager.createNotificationChannel(channel);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "n";
            String description = "d";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("12345", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
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
                mspent.setText("30");
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
            if(z >= 0) {
                walletCash = Integer.toString(z);  //This line converts integer to String
                wCash.setText(walletCash);
            }
            else {
                Context context = getApplicationContext();
                CharSequence text = "Balance too low";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        } catch(NumberFormatException exc) {
            st  = "0";
        }
        wCash = findViewById(R.id.wallet_cash);
        count++;
        saveTrans(st);

        //Determine which button is pressed
        int id = view.getId();
        switch(id) {
            case R.id.button_food:
                food.add(Integer.parseInt(st));
                break;
            case R.id.button_travel:
                travel.add(Integer.parseInt(st));
                break;
            case R.id.button_others:
                others.add(Integer.parseInt(st));
                break;
        }
    }

    public void updateCash(String add) {
        try {
            int x = Integer.parseInt(walletCash);
            int y = Integer.parseInt(add);
            int z = x + y;    //Deduct money spent

            walletCash = Integer.toString(z);  //This line converts integer to String
            wCash.setText(walletCash);


        } catch(NumberFormatException exc) {
            st  = "0";
        }
    }

    //Add money to the wallet by tapping the plus button
    public void addMoney(View view) {

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialogs, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//        alertDialogBuilder.setTitle("Add Money");
        alertDialogBuilder.setView(promptView);

        final EditText addAmount = promptView.findViewById(R.id.wallet_add_input);
        //setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //aText.setText("Hello, " + addAmount.getText());
                        //walletCash = walletCash + addAmount.toString();
                        updateCash(addAmount.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
        Button neutralButton = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#17202A")));
        /*// Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this);

        // Set the message show for the Alert time
        builder.setMessage("Enter amount to add");

        // Set Alert Title
        builder.setTitle("Add Money");

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(true);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
      //  alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color);*/
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
        wCash = findViewById(R.id.wallet_cash);
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

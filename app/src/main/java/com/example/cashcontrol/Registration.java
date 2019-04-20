package com.example.cashcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    String name;
    String cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

    }

    public void sendDetails(View view) {
        //Get registration details from user
        TextView nameUser  = (TextView) findViewById(R.id.nameTextView);
        name = nameUser.getText().toString();

        EditText iniCash = (EditText)findViewById(R.id.initalCashTextView);
        //Get the wallet cash in the form of string
        cash = iniCash.getText().toString();

        //Cast the Wallet Cash of String type to integer
        //int cash  = Integer.parseInt(Wcash);
        Intent intent = new Intent(Registration.this , MainActivity.class);
        intent.putExtra("name" , name);
        intent.putExtra("cash" , cash);
        startActivity(intent);
    }

}

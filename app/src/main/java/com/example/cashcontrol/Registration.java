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

    public void continueRegistration(View view) {
        //get to setp 2 of registration
        //Get registration details from user
        TextView nameUser  = findViewById(R.id.nameTextView);
        name = nameUser.getText().toString();

        EditText iniCash = findViewById(R.id.initalCashTextView);
        //Get the wallet cash in the form of string
        cash = iniCash.getText().toString();
        Intent intent = new Intent(Registration.this, Registration1.class);
        intent.putExtra("name" , name);
        intent.putExtra("cash" , cash);
        startActivity(intent);
    }

}

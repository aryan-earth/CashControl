package com.example.cashcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Registration1 extends AppCompatActivity {
    public String cash;
    public String name;
    public String food_limit;
    public String travel_limit;
    public String others_limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        //Get user details from first run
        name = getIntent().getStringExtra("name");
        cash = getIntent().getStringExtra("cash");//till here everything works
    }

    public void finishRegistration(View view) {
        TextView food = findViewById(R.id.food_limit);
        TextView travel = findViewById(R.id.travel_limit);
        TextView others = findViewById(R.id.others_limit);
        food_limit = food.getText().toString();
        travel_limit = travel.getText().toString();
        others_limit = others.getText().toString();
        Intent intent = new Intent(Registration1.this, MainActivity.class);
        intent.putExtra("name" , name);
        intent.putExtra("cash" , cash);
        intent.putExtra("food_limit", food_limit);
        intent.putExtra("travel_limit", travel_limit);
        intent.putExtra("others_limit", others_limit);
        startActivity(intent);
    }

}

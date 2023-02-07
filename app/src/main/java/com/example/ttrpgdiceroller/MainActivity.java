package com.example.ttrpgdiceroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rolld4btn = findViewById(R.id.d4btn);
        Button rolld6btn = findViewById(R.id.d6btn);
        Button rolld8btn = findViewById(R.id.d8btn);
        Button rolld10btn = findViewById(R.id.d10btn);
        Button rolld12btn = findViewById(R.id.d12btn);
        Button rolld20btn = findViewById(R.id.d20btn);


        rolld4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRollDiceActivity(4);

            }
        });

        rolld6btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRollDiceActivity(6);

            }
        });

        rolld8btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRollDiceActivity(8);

            }
        });

        rolld10btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRollDiceActivity(10);

            }
        });

        rolld12btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRollDiceActivity(12);

            }
        });

        rolld20btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRollDiceActivity(20);

            }
        });
    }

    private void openRollDiceActivity(int diceType){
        Intent intent = new Intent(MainActivity.this, rollDiceActivity.class);
        intent.putExtra("diceType", diceType);
        startActivity(intent);
    }


}
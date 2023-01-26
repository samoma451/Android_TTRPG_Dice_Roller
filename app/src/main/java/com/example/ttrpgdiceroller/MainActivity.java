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
        Button rolld20btn = findViewById(R.id.d20btn);

        rolld4btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, rollDiceActivity.class);
                intent.putExtra("diceType", 4);
                startActivity(intent);

            }
        });

        rolld6btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, rollDiceActivity.class);
                intent.putExtra("diceType", 6);
                startActivity(intent);

            }
        });

        rolld8btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, rollDiceActivity.class);
                intent.putExtra("diceType", 8);
                startActivity(intent);

            }
        });

        rolld20btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, rollDiceActivity.class);
                intent.putExtra("diceType", 20);
                startActivity(intent);

            }
        });
    }


}
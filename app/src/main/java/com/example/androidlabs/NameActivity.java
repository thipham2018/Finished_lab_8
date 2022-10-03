package com.example.androidlabs;


import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.jar.Attributes;


import android.widget.TextView;

public class NameActivity extends AppCompatActivity {
    TextView textView2;
    Button button2;
    Button button3;
    String name;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);


        TextView textView2 = findViewById(R.id.textView2);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");

        TextView textView1 = findViewById(R.id.textView2);
        textView2.setText("welcome,"+ getIntent().getExtras().getString("Name"));


     Button   button2 = findViewById(R.id.button2);
     Button   button3 = findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText.getText().toString();
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                textView2.setText(editText.getText().toString());
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("Name", name);
                setResult(RESULT_OK, intent);
                finish();

            }
        });


            }

        }


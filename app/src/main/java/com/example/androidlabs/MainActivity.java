package com.example.androidlabs;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {


    EditText editText;
    Button button1;
    TextView textView1;
    String name;
    SharedPreferences sharedPreferences;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final int REQ_CODE=100;
    public static final int MODE_APPEND =32768;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView1 = (TextView) findViewById(R.id.textView1);
        name = editText.getText().toString();
        button1 = (Button) findViewById(R.id.button1);

        sharedPreferences = getSharedPreferences("Shared_Prefs", Context.MODE_PRIVATE);


         button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, NameActivity.class);

                intent.putExtra("Name", editText.getText().toString());

               startActivityForResult(intent, 2);

            }


      });

    }


      @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){

            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                String name = data.getStringExtra("name");
                editText.setText(data.getStringExtra("name"));
            }
        }
    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("Shared_Prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("name", editText.getText().toString());
        myEdit.apply();
    }


    }



package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TODO> elements;
    private MyListAdapter myAdapter;
    TODO todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText) findViewById(R.id.editText);
        SwitchCompat switchCompat = findViewById(R.id.switch2);
        Button addButton = findViewById(R.id.myButton);
        elements = new ArrayList<>();

        addButton.setOnClickListener(click -> {
            String listItem = editText.getText().toString();
            todo = new TODO();
            todo.setTodoText(listItem);
            todo.setUrgent(switchCompat.isChecked());
            elements.add(todo);
            myAdapter.notifyDataSetChanged();
            editText.setText("");

            switchCompat.setChecked(false);
        });



        ListView myList = findViewById(R.id.thelistView);
        myList.setAdapter(myAdapter = new MyListAdapter());

       myList.setOnItemClickListener((parent, view, pos, id) -> {

        });

        myList.setOnItemLongClickListener((p, b, pos, id) -> {

           View newView = getLayoutInflater().inflate(R.layout.item_list, null);
           TextView tView = newView.findViewById(R.id.textlist);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Do you want to delete this?")

                    .setMessage("The selected row is: " + pos +
                            "\n " + elements.get(pos).todoText)

                    .setPositiveButton("Yes", (click, arg) -> {
                        elements.remove(elements.get(pos));
                        myAdapter.notifyDataSetChanged();
                    })

                    .setNegativeButton("No", (click, arg) -> {
                    })
                    .setView(newView)

                    .create().show();
                    return true;
        });

    }


    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return elements.size();
        }

        public TODO getItem(int position) {
            return elements.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View old, ViewGroup parent) {

            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row:
            if (newView == null) {
                newView = inflater.inflate(R.layout.item_list, parent, false);
            }

            //set what the text should be for this row:
            TextView tView = newView.findViewById(R.id.textlist);
            tView.setText(getItem(position).todoText);

            if (getItem(position).isUrgent) {
                tView.setBackgroundColor(Color.RED);
                tView.setTextColor(Color.WHITE);
            } else {
                tView.setBackgroundColor(Color.WHITE);
                tView.setTextColor(Color.GRAY);
            }

            return newView;
        }
    }
}

    class TODO {
        String todoText;
        boolean isUrgent;

        public String getTodoText() {
            return todoText;
        }

        public void setTodoText(String todoText) {
            this.todoText = todoText;
        }

        public boolean isUrgent() {
            return isUrgent;
        }

        public void setUrgent(boolean urgent) {
            isUrgent = urgent;
        }
    }




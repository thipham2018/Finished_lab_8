package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        SwitchCompat switchCompat = findViewById(R.id.switch2);
        Button addButton = findViewById(R.id.myButton);

        elements = new ArrayList<>();

        loadDataFromDatabase();

        addButton.setOnClickListener(click -> {
            String listItem = editText.getText().toString();
            todo = new TODO();
            todo.setTodoText(listItem);
            todo.setUrgent(switchCompat.isChecked());

            //add to db & get new ID
            ContentValues newRowValues = new ContentValues();

            //Put String into the Items column
            newRowValues.put(MyOpener.COL_ITEMS, listItem);
            newRowValues.put(MyOpener.COL_URGENT, switchCompat.isChecked());

            //insert into db
            long newID = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

            //Create entry item
            //adding third parameter to constructor
            todo = new TODO(listItem, newID, switchCompat.isChecked());

            elements.add(todo);

            editText.setText("");
            switchCompat.setChecked(false);
            myAdapter.notifyDataSetChanged();
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
    private  void loadDataFromDatabase(){
            MyOpener dbOpener = new MyOpener(this);
            db = dbOpener.getWritableDatabase();
            String[] columns = {MyOpener.COL_ID, MyOpener.COL_ITEMS, MyOpener.COL_URGENT};


            Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null,
                null, null, null);
                int nameCoLIndex = results.getColumnIndex(MyOpener.COL_ITEMS);
                int idCoIndex = results.getColumnIndex(MyOpener.COL_ID);
                int urgentCoLIndex = results.getColumnIndex(MyOpener.COL_URGENT);

                while (results.moveToNext()){
                     String name = results.getString(nameCoLIndex);
                     long id = results.getLong(idCoIndex);
                     boolean urgent = (results.getInt(urgentCoLIndex) !=0);
                     elements.add(new TODO(name, id, urgent));
                  }
                      }
                protected void updateContact(TODO c){
                    ContentValues updatedValues = new ContentValues();
                    updatedValues.put(MyOpener.COL_ITEMS, c.getTodoText());
                    db.update(MyOpener.TABLE_NAME, updatedValues, MyOpener.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
        }

                protected void deleteContact(TODO c) {
                    db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{Long.toString(c.getId())});
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

            //set the text should be for this row:
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
        protected long id;
        public TODO(){

        }
        public  TODO(String n, long i){
            todoText = n;
            id = i;
        }
        public TODO(String n, long i, boolean urgent) {
            todoText = n;
            id = i;
            isUrgent = urgent;
        }

        public void update(String n){
            todoText = n;
        }
        public String getTodoText() {

            return todoText;
        }
        public long getId(){
            return  id;
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




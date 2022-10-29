package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String url = "https://swapi.dev/api/people/?format=json";
    private ArrayList<Characters> characterList = new ArrayList<>();
    private TextView textView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new StarWars().execute(url);
    }

    private class StarWars extends AsyncTask<String, String, ArrayList<Characters>> {
        private JSONArray charList = new JSONArray();

        @Override
        protected ArrayList<Characters> doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String jsonStr = stringBuilder.toString();
                JSONObject obj = new JSONObject(jsonStr);
                charList = (JSONArray) obj.get("results");
                for (int i = 0; i < charList.length(); i++) {
                    JSONObject jObj = (JSONObject) charList.get(i);
                    characterList.add(new Characters(
                                    jObj.getString("name"),
                                    jObj.getString("height"),
                                    jObj.getString("mass"),
                                    jObj.getString("hair_color"),
                                    jObj.getString("skin_color"),
                                    jObj.getString("eye_color"),
                                    jObj.getString("birth_year"),
                                    jObj.getString("gender"),
                                    jObj.getString("homeworld")
                            )
                    );
                }
                bufferedReader.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return characterList;
        }

        @Override
        protected void onPostExecute(ArrayList<Characters> characters) {
            super.onPostExecute(characters);
            listView = findViewById(R.id.listView);
            ArrayList<String> names = new ArrayList<>();
            for (Characters x:characters){
                names.add(x.getName());
            }
            ArrayAdapter adapter = new ArrayAdapter(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    names
            );
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (findViewById(R.id.frameLayout)==null) {
                        Intent intent = new Intent(MainActivity.this, DetailsStarWars.class);
                        intent.putExtra("Character", characters.get(i));
                        startActivity(intent);
                    }
                    else{
                        DetailsFragment fragment = new DetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Character",characters.get(i));
                        fragment.setArguments(bundle);
                        replaceFragment(fragment);
                    }
                }
            });
        }
    }

    public void replaceFragment(DetailsFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}
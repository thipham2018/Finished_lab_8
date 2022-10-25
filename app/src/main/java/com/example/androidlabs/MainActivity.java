package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import com.example.androidlabs.Callback;

public class MainActivity extends AppCompatActivity implements Callback {
    private VideoView videoView;
    private ImageView imageView;
    private String route = "https://cataas.com/cat";
    private String root = "https://cataas.com";
    private String url = "https://cataas.com/cat?json=true";
    private String res;
    private HashMap<String, Bitmap> picList = new HashMap<>();
    private ProgressBar progressBar;
    private CatImages catImages = new CatImages();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView2);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(2000);
        startAsyncTask();
    }

    private void startAsyncTask() {
        catImages= new CatImages();
        catImages.callback = (Callback) this;
        catImages.execute(url);
    }

    @Override
    public void callBack(String result) {
        startAsyncTask();
    }
    private class CatImages extends AsyncTask<String, Integer, Bitmap> {
        public Callback callback;
        StringBuilder stringBuilder = new StringBuilder();
        private Bitmap bitmap;
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            try {
                url = new URL(strings[0]);
                URLConnection urlConnection = url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                String jsonStr = stringBuilder.toString();
                JSONObject obj = new JSONObject(jsonStr);
                if (picList.containsKey(obj.getString("_id"))) {
                    res = "id " + obj.getString("_id");
                    bitmap = picList.get(obj.getString("_id"));
                } else {
                    url = new URL(root+obj.getString("url"));
                    InputStream inputStream = url.openConnection().getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    picList.put(obj.getString("_id"), bitmap);
                }
                bufferedReader.close();
                Log.v("TAG", "publish progress");
                //  publishProgress(res);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //update progress bar
            for (int i=0;i<100;i++){
                publishProgress(20);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap !=null){
                imageView.setImageBitmap(bitmap);
                Log.v("TAG", "Done set picture");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.callBack(url);
            }
            else{
                Toast.makeText(MainActivity.this, "Bitmap is null", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (progressBar.getProgress() == 2000) progressBar.setProgress(0);
            progressBar.setProgress(progressBar.getProgress()+values[0]);
        }
    }


}













































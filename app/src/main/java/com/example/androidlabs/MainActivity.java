package com.example.androidlabs;

import android.os.Bundle;

public class MainActivity extends ExtendActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        super.initToolbar();
        super.initDrawerLayout();
        super.setCurrent(this);
    }
}
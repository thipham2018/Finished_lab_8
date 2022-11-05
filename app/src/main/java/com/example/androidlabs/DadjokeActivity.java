package com.example.androidlabs;

import android.os.Bundle;

public class DadjokeActivity extends ExtendActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_dadjoke);

        super.initToolbar();
        super.initDrawerLayout();
        super.setCurrent(this);
    }
}
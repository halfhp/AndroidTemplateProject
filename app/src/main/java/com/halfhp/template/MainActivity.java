package com.halfhp.template;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;

@EActivity
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @AfterInject
    void afterInject() {
        // Do something cool!
    }
}

package com.bignerdranch.android.tingle;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tingle);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_ui);
        Fragment fragmentList = fm.findFragmentById(R.id.fragment_container_list);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (fragment == null || fragmentList == null) {
                fragment = new UIFragment();
                fragmentList = new ListFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container_ui, fragment)
                        .add(R.id.fragment_container_list, fragmentList).commit();
            }
        }
        else{
            if (fragment == null) {
                fragment = new UIFragment();
                fm.beginTransaction().add(R.id.fragment_container_ui, fragment).commit();
            }
        }
    }
}
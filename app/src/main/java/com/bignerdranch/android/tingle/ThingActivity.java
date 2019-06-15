package com.bignerdranch.android.tingle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class ThingActivity extends AppCompatActivity {

   private static final String EXTRA_THING_ID = "com.bignerdranch.android.tingle.thing_id";

    public static Intent newIntent(Context packageContext, UUID thingId) {
        Intent intent = new Intent(packageContext, ThingActivity.class);
        intent.putExtra(EXTRA_THING_ID, thingId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container_thing);

        if (fragment == null) {
            UUID thingId = (UUID) getIntent()
                    .getSerializableExtra(EXTRA_THING_ID);
            fragment = ThingFragment.newInstance(thingId);
            fm.beginTransaction()
                    .add(R.id.fragment_container_thing, fragment)
                    .commit();
        }
    }
}

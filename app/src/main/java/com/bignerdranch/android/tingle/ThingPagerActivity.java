package com.bignerdranch.android.tingle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;


public class ThingPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Thing> mThings;

    private static final String EXTRA_THING_ID = "com.bignerdranch.android.tingle.thing_id";


    public static Intent newIntent(Context packageContext, UUID thingId) {
        Intent intent = new Intent(packageContext, ThingPagerActivity.class);
        intent.putExtra(EXTRA_THING_ID, thingId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thing_pager);

        UUID thingId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_THING_ID);

        mViewPager = (ViewPager) findViewById(R.id.thing_view_pager);

        mThings = ThingsDB.get(this).getThingsDB();
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {

            @Override
            public Fragment getItem(int position) {
                Thing thing = mThings.get(position);
                return ThingFragment.newInstance(thing.getThingID());
            }

            @Override
            public int getCount() {
                return mThings.size();
            }
        });

        for (int i = 0; i< mThings.size(); i++) {
            if (mThings.get(i).getThingID().equals(thingId)) {
                mViewPager.setCurrentItem(i);
            }
        }
    }
}

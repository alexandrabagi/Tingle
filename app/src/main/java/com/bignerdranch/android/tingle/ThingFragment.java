package com.bignerdranch.android.tingle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;

public class ThingFragment extends Fragment {

    private Thing mThing;
    private TextView mThingNo;
    private TextView mThingWhat;
    private TextView mThingWhere;

    private static final String ARG_THING_ID = "thing_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID thingId = (UUID) getArguments()
                .getSerializable(ARG_THING_ID);
        mThing = ThingsDB.get(getActivity()).getThing(thingId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_thing, container, false);
        // pg. 145 methods
        //mThingNo = (TextView) v.findViewById(R.id.fragment_thing_no);
        //mThingNo.setText(ThingsDB.get().getThingsDB().indexOf(mThing));
        mThingWhat = (TextView) v.findViewById(R.id.fragment_thing_what);
        mThingWhat.setText(mThing.getWhat());
        mThingWhere = (TextView) v.findViewById(R.id.fragment_thing_where);
        mThingWhere.setText(mThing.getWhere());

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        ThingsDB.get(getActivity())
                .updateThing(mThing);
    }

    public static ThingFragment newInstance(UUID thingId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_THING_ID, thingId);

        ThingFragment fragment = new ThingFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

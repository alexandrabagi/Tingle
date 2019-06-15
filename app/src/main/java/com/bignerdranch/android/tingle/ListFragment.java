package com.bignerdranch.android.tingle;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ListFragment extends Fragment implements Observer {

    private ThingsDB thingsDB;
    private List<Thing> mThings;
    private RecyclerView mThingsRecyclerView;
    private ThingAdapter mAdapter;

    @Override
    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thingsDB = ThingsDB.get(getActivity());
        mThings = thingsDB.getThingsDB();
        thingsDB.addObserver(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mThingsRecyclerView = (RecyclerView) v.findViewById(R.id.things_recycler_view);
        mThingsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration divider = new DividerItemDecoration(mThingsRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        //divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.custom_divider));
        mThingsRecyclerView.addItemDecoration(divider);

        updateUI();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ThingsDB thingsDB = ThingsDB.get(getActivity());
        List<Thing> things = thingsDB.getThingsDB();

        if (mAdapter == null) {
            mAdapter = new ThingAdapter(mThings);
            mThingsRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.setThings(things);
            mAdapter.notifyDataSetChanged();
        }
    }

    //define the ViewHolder
    private class ThingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Thing mThing;
        //private TextView noView;
        private TextView whatView;
        private TextView whereView;

        public ThingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item, parent, false));
            //noView = itemView.findViewById(R.id.thing_no);
            whatView = itemView.findViewById(R.id.thing_what);
            whereView = itemView.findViewById(R.id.thing_where);
            itemView.setOnClickListener(this);
        }

        public void bind(Thing thing, int i) {
            mThing = thing;
            //noView.setText(" " + i + " ");
            whatView.setText(mThing.getWhat());
            whereView.setText(mThing.getWhere());
        }

        @Override
        public void onClick(View view) {

            Intent intent =ThingPagerActivity.newIntent(getActivity(), mThing.getThingID());
            startActivity(intent);

        }
    }

    //define the adapter
    private class ThingAdapter extends RecyclerView.Adapter<ThingHolder> {

        private List<Thing> mThings;

        public ThingAdapter(List<Thing> things) {
            mThings = things;
        }

        @NonNull
        @Override
        public ThingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ThingHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ThingHolder holder, int position) {
            Thing thing = mThings.get(position);
            holder.bind(thing, position);
        }

        @Override
        public int getItemCount() {
            return mThings.size();
        }

        public void setThings(List<Thing> things) {
            mThings = things;
        }
    }
}



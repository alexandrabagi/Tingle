package com.bignerdranch.android.tingle;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UIFragment extends Fragment {

    // GUI variables
    private Button listThings;
    private Button addThings;
   // private Button deleteThings;
    private EditText whatBox;
    private EditText whereBox;

    //Model: Database of things
    private ThingsDB thingsDB;

    private TextView apiVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thingsDB = ThingsDB.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ui, container, false);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            listThings = (Button) v.findViewById(R.id.list_button);
            listThings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                } /*else {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    //if statement to prevent list things twice
                    ft.add(R.id.fragment_container_list, new ListFragment());
                    ft.commit();
                }*/
            });
        }

        whatBox = (EditText) v.findViewById(R.id.what_text_input);
        whereBox = (EditText) v.findViewById(R.id.where_text_input);

        addThings = (Button) v.findViewById(R.id.add_button);
        addThings.setEnabled(true);

        addThings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whatContent = whatBox.getText().toString().trim();
                String whereContent = whereBox.getText().toString().trim();
                if(!whatContent.isEmpty() && !whereContent.isEmpty()) {
                    Thing t = new Thing(whatContent, whereContent);
                    thingsDB.addThing(t);
                }
                whatBox.setText("");
                whereBox.setText("");
            }
        });

        int version = Build.VERSION.SDK_INT;
        apiVersion = v.findViewById(R.id.api_version);
        apiVersion.setText("API level " + version);

        return v;
    }
}


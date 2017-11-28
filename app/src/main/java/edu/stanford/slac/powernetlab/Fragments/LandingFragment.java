package edu.stanford.slac.powernetlab.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.stanford.slac.powernetlab.R;

/**
 * Created by supriyap on 11/3/17.
 */

public class LandingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        return view;
    }
}

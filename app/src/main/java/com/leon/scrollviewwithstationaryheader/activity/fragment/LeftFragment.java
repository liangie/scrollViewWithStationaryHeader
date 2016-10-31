package com.leon.scrollviewwithstationaryheader.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.scrollviewwithstationaryheader.R;

/**
 * Created by leon on 10/27/16.
 */
public class LeftFragment extends Fragment {
    private View root;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_left,null);
        return root;
    }
}

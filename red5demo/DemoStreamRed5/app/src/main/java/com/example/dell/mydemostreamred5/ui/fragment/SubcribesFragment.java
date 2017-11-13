package com.example.dell.mydemostreamred5.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.mydemostreamred5.MainActivity;
import com.example.dell.mydemostreamred5.R;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.source.R5VideoSource;
import com.red5pro.streaming.view.R5VideoView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubcribesFragment extends Fragment {
    private Button btnSubcribes;
    private R5VideoView r5VideoView;
    private boolean isSubcribe = false;
    private R5Stream r5Stream;

    public SubcribesFragment() {
        // Required empty public constructor
    }

    public static SubcribesFragment newInstance() {
        SubcribesFragment fragment = new SubcribesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_subcribes, container, false);
        btnSubcribes = (Button) v.findViewById(R.id.btnsubcribes);
        r5VideoView = (R5VideoView) v.findViewById(R.id.videoview);

        btnSubcribes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubcribeToggle();
            }
        });
        return v;
    }

    private void onSubcribeToggle() {
        if (isSubcribe)
            stopSubcribes();
        else
            startSubcribes();
        isSubcribe=!isSubcribe;

    }

    private void startSubcribes() {
        r5Stream = new R5Stream(new R5Connection(MainActivity.r5Configuration));
        r5VideoView.attachStream(r5Stream);
        r5Stream.play("113");
        Toast.makeText(getActivity(), "Start subcribe ", Toast.LENGTH_SHORT).show();

    }

    private void stopSubcribes() {
        Toast.makeText(getActivity(), "Stop subcribe ", Toast.LENGTH_SHORT).show();

        if(r5Stream!=null)
            r5Stream.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(isSubcribe)
            stopSubcribes();
    }
}

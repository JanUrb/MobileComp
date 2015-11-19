package com.example.jaur.mvc_frag;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;


/**
 * Fragment, welches die Kursanzeige implementiert.
 *
 * @author jaur
 */
public class CourseFragment extends Fragment implements Observer {
    private final static String TAG = "jaur6386CourseFragment";
    private LocationModel lm;
    TextView textView;

    public CourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        this.lm = ((MainActivity) getActivity()).getLocationModel();
        this.lm.addObserver(this);

        View view = inflater.inflate(R.layout.fragment_course, container, false);

        textView = (TextView) view.findViewById(R.id.course_text);

        return view;
    }


    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        lm.deleteObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "Course: " + lm.getCourse());
        textView.setText(" " + lm.getCourse());
    }
}

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
 * Fragment, welches die Geschwindigkeitsanzeige implementiert.
 *
 * @author jaur
 */
public class SpeedFragment extends Fragment implements Observer {

    private final static String TAG = "jaur6386SpeedFragment";
    private TextView textView;
    private LocationModel lm;

    public SpeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        lm = ((MainActivity) getActivity()).getLocationModel();
        lm.addObserver(this);
        View view = inflater.inflate(R.layout.fragment_speed, container, false);

        this.textView = (TextView) view.findViewById(R.id.speed_text);
        // Inflate the layout for this fragment
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
        Log.d(TAG, "update speeed: " + lm.getSpeed());
        this.textView.setText(String.format("%.2f", lm.getSpeed()));
    }
}

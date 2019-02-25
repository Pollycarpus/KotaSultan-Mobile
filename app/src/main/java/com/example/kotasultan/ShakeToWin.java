package com.example.kotasultan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShakeToWin extends Fragment {

    private SensorManager mSensorManager;

    private ShakeEventListener mSensorListener;

    public ShakeToWin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_shake_to_win, container, false);

        final ImageView mCoinImage = (ImageView) view.findViewById(R.id.coinImage);
        final TextView mCoinText = (TextView) view.findViewById(R.id.coinText);

        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {
                int random = new Random().nextInt(6);
                int coinValue = 20 * random;

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("You got " + coinValue + " coins!");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                if (!ShakeEventListener.getShakeAvailable()) {
                    mCoinText.setText("Shake again tomorrow!");
                    mCoinImage.setImageResource(R.drawable.business_inactive);
                    Log.d("GetShakeAvailable", "FALSE");
                } else {
                    mCoinText.setText("Shake your phone!");
                    mCoinImage.setImageResource(R.drawable.business);
                    Log.d("GetShakeAvailable", "TRUE");
                }
            }

        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}

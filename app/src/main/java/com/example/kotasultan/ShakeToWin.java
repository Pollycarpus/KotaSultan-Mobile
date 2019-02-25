package com.example.kotasultan;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
                long coinValue = 20 * random;
                Map<String,Object> userKoin = new HashMap<>();
                long total = (long) Catatanku.currKoin + coinValue;
                Number value = (Number) total;
                userKoin.put("koin",value);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document(MainActivity.Uid)
                        .set(userKoin)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("catatanku","success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("catatanku","failed",e);
                            }
                        });

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

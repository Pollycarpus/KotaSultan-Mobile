package com.example.kotasultan;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class Catatanku extends Fragment {

    public static final String EXTRA_MESSAGE1 = "Nama";
    public static final String EXTRA_MESSAGE2 = "Nilai";
    public static final String EXTRA_MESSAGE3 = "Tanggal";
    public static final String EXTRA_MESSAGE4 = "Koin";

    private TextView mCurrencyPengeluaran,mCurrencyPemasukan,mCurrentKoin;
    private EditText editPengeluaran;
    private  EditText editValuePengeluaran;
    private  EditText editDatePengeluaran;
    private  EditText editPemasukan;
    private  EditText editValuePemasukan;
    private  EditText editDatePemasukan;

    public Catatanku() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_catatanku, container, false);

        mCurrentKoin = view.findViewById(R.id.koin_val);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(MainActivity.Uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot user = task.getResult();
                            if (user.contains("koin")){
                                Number koin = (Number) user.get("koin");
                                mCurrentKoin.setText(koin.toString());
                            }else{
                                Log.d("catatanku", "coin not found");
                            }
                        }else{
                            Log.d("catatanku", "failure getting document", task.getException());
                        }
                    }
                });

        Button buttonPengeluaran = (Button) view.findViewById(R.id.button_pengeluaran);
        buttonPengeluaran.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editPengeluaran = (EditText) getView().findViewById(R.id.editPengeluaran);
                editValuePengeluaran = (EditText) getView().findViewById(R.id.editValuePengeluaran);
                editDatePengeluaran = (EditText) getView().findViewById(R.id.editDatePengeluaran);
                mCurrencyPengeluaran =(TextView) getView().findViewById(R.id.currency);

                Map<String,Object> pengeluaran = new HashMap<>();
                pengeluaran.put("nama",editPengeluaran.getText().toString());
                pengeluaran.put("nilai",editValuePengeluaran.getText().toString());
                pengeluaran.put("tanggal",editDatePengeluaran.getText().toString());
                pengeluaran.put("currency",mCurrencyPengeluaran.getText().toString());
                pengeluaran.put("jenis","pengeluaran");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("History").document(MainActivity.Uid).collection("data")
                        .add(pengeluaran)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("catatanku","success");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("catatanku","failed",e);
                    }
                });


                Intent intent = new Intent(getActivity(), DisplayInputActivity.class);

                String message1 = editPengeluaran.getText().toString();
                String message2 = editValuePengeluaran.getText().toString();
                String message3 = editDatePengeluaran.getText().toString();
                long nilai = Long.parseLong(message2);
                long coinVal = convertToCoin(nilai);
                String message4 = Long.toString(coinVal);
                long koin = Long.parseLong(mCurrentKoin.getText().toString()) + coinVal;
                Map<String,Object> userKoin = new HashMap<>();
                Number value = (Number) koin;
                userKoin.put("koin",value);
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

                intent.putExtra(EXTRA_MESSAGE1, message1);
                intent.putExtra(EXTRA_MESSAGE2, message2);
                intent.putExtra(EXTRA_MESSAGE3, message3);
                intent.putExtra(EXTRA_MESSAGE4, message4);

                startActivity(intent);
            }

            public long convertToCoin(long number){
                int max = 1000;

                if (number / 100 > max){
                    return max;
                }else{
                    return number / 100;
                }
            }
        });

        Button buttonPemasukan = (Button) view.findViewById(R.id.button_pemasukan);
        buttonPemasukan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editPemasukan = (EditText) getView().findViewById(R.id.editPemasukan);
                editValuePemasukan = (EditText) getView().findViewById(R.id.editValuePemasukan);
                editDatePemasukan = (EditText) getView().findViewById(R.id.editDatePemasukan);
                mCurrencyPemasukan = (TextView) getView().findViewById(R.id.currency2);

                Map<String,Object> pengeluaran = new HashMap<>();
                pengeluaran.put("nama",editPemasukan.getText().toString());
                pengeluaran.put("nilai",editValuePemasukan.getText().toString());
                pengeluaran.put("tanggal",editDatePemasukan.getText().toString());
                pengeluaran.put("currency",mCurrencyPemasukan.getText().toString());
                pengeluaran.put("jenis","pemasukan");
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("History").document(MainActivity.Uid).collection("data")
                        .add(pengeluaran)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("catatanku","success");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("catatanku","failed",e);
                    }
                });

                Intent intent = new Intent(getActivity(), DisplayInputActivity.class);

                String message1 = editPemasukan.getText().toString();
                String message2 = editValuePemasukan.getText().toString();
                String message3 = editDatePemasukan.getText().toString();
                long nilai = Long.parseLong(message2);
                long coinVal = convertToCoin(nilai);
                String message4 = Long.toString(coinVal);
                long koin = Long.parseLong(mCurrentKoin.getText().toString()) + coinVal;
                Map<String,Object> userKoin = new HashMap<>();
                Number value = (Number) koin;
                userKoin.put("koin",value);
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

                intent.putExtra(EXTRA_MESSAGE1, message1);
                intent.putExtra(EXTRA_MESSAGE2, message2);
                intent.putExtra(EXTRA_MESSAGE3, message3);
                intent.putExtra(EXTRA_MESSAGE4, message4);

                startActivity(intent);
            }

            public long convertToCoin(long number){
                int max = 1000;

                if (number / 100 > max){
                    return max;
                }else{
                    return number / 100;
                }
            }
        });

        return view;
    }
}

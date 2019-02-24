package com.example.kotasultan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class Catatanku extends Fragment {

    public static final String EXTRA_MESSAGE1 = "Nama";
    public static final String EXTRA_MESSAGE2 = "Nilai";
    public static final String EXTRA_MESSAGE3 = "Tanggal";
    public static final String EXTRA_MESSAGE4 = "Koin";

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
        return inflater.inflate(R.layout.fragment_catatanku, container, false);
    }

    public void sendPengeluaran(View view) {

        editPengeluaran = (EditText) getView().findViewById(R.id.editPengeluaran);
        editValuePengeluaran = (EditText) getView().findViewById(R.id.editValuePengeluaran);
        editDatePengeluaran = (EditText) getView().findViewById(R.id.editDatePengeluaran);

        Intent intent = new Intent(getActivity(), DisplayInputActivity.class);

        String message1 = editPengeluaran.getText().toString();
        String message2 = editValuePengeluaran.getText().toString();
        String message3 = editDatePengeluaran.getText().toString();
        long nilai = Long.parseLong(message2);
        long coinVal = convertToCoin(nilai);
        String message4 = Long.toString(coinVal);

        intent.putExtra(EXTRA_MESSAGE1, message1);
        intent.putExtra(EXTRA_MESSAGE2, message2);
        intent.putExtra(EXTRA_MESSAGE3, message3);
        intent.putExtra(EXTRA_MESSAGE4, message4);

        startActivity(intent);
    }

    public void sendPemasukan(View view) {

        editPemasukan = (EditText) getView().findViewById(R.id.editPemasukan);
        editValuePemasukan = (EditText) getView().findViewById(R.id.editValuePemasukan);
        editDatePemasukan = (EditText) getView().findViewById(R.id.editDatePemasukan);

        Intent intent = new Intent(getActivity(), DisplayInputActivity.class);

        String message1 = editPemasukan.getText().toString();
        String message2 = editValuePemasukan.getText().toString();
        String message3 = editDatePemasukan.getText().toString();
        long nilai = Long.parseLong(message2);
        long coinVal = convertToCoin(nilai);
        String message4 = Long.toString(coinVal);

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
}

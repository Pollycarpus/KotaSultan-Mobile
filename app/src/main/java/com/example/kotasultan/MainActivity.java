package com.example.kotasultan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE1 = "Nama";
    public static final String EXTRA_MESSAGE2 = "Nilai";
    public static final String EXTRA_MESSAGE3 = "Tanggal";
    public static final String EXTRA_MESSAGE4 = "Koin";

    private  EditText editPengeluaran;
    private  EditText editValuePengeluaran;
    private  EditText editDatePengeluaran;
    private  EditText editPemasukan;
    private  EditText editValuePemasukan;
    private  EditText editDatePemasukan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPengeluaran = (EditText) findViewById(R.id.editPengeluaran);
        editValuePengeluaran = (EditText) findViewById(R.id.editValuePengeluaran);
        editDatePengeluaran = (EditText) findViewById(R.id.editDatePengeluaran);

        editPemasukan = (EditText) findViewById(R.id.editPemasukan);
        editValuePemasukan = (EditText) findViewById(R.id.editValuePemasukan);
        editDatePemasukan = (EditText) findViewById(R.id.editDatePemasukan);
    }

    public void sendPengeluaran(View view) {
        Intent intent = new Intent(this, DisplayInputActivity.class);

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
        Intent intent = new Intent(this, DisplayInputActivity.class);

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

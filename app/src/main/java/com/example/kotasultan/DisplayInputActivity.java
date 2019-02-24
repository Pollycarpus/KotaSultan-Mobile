package com.example.kotasultan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayInputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_input);

        Intent intent = getIntent();
        String message1 = intent.getStringExtra(Catatanku.EXTRA_MESSAGE1);
        String message2 = intent.getStringExtra(Catatanku.EXTRA_MESSAGE2);
        String message3 = intent.getStringExtra(Catatanku.EXTRA_MESSAGE3);
        String coinVal = intent.getStringExtra(Catatanku.EXTRA_MESSAGE4);

        TextView textView1 = findViewById(R.id.textView1);
        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        TextView textView4 = findViewById(R.id.textView4);

        textView1.setText(message1);
        textView2.setText(message2);
        textView3.setText(message3);
        textView4.setText(coinVal);

    }
}

package com.example.kotasultan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    SharedPreferences prefs;
    Switch switchTheme;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        switchTheme = findViewById(R.id.switch1);
        prefs = getSharedPreferences("prefTheme", Context.MODE_PRIVATE);
        editor = prefs.edit();

        if (switchTheme != null) {
            switchTheme.setOnCheckedChangeListener(this);
        }

        boolean check = prefs.getBoolean("prefKey", false);
        switchTheme.setChecked(check);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            editor.putBoolean("prefKey", true);
        } else {
            editor.putBoolean("prefKey", false);
        }
    }
}

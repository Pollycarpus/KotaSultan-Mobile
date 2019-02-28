package com.example.kotasultan;

import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    public static String Uid;
    private FloatingActionButton fab;
    private SharedPreferences myPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Uid==null){
            Intent intent = getIntent();
            Uid = intent.getStringExtra("Uid");
        }

        // Start background music
        callPlay();

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        myPrefs = getApplicationContext().getSharedPreferences("prefTheme", Context.MODE_PRIVATE);
        boolean check = myPrefs.getBoolean("prefKey", false);

        // Set Setting Button
        fab = findViewById(R.id.settingButton);
        fab.setOnClickListener(
          new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
              }
          }
        );
        if (check) {
            tabLayout.setBackgroundColor(Color.RED);
        }

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               viewPager.setCurrentItem(tab.getPosition());
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {
           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {
           }
       });
    }

    public void callPlay() {
        Intent intent = new Intent(MainActivity.this, BackgroundMusicService.class);
        startService(intent);
    }

    public void stopPlay() {
        Intent intent = new Intent(MainActivity.this, BackgroundMusicService.class);
        stopService(intent);
    }

    @Override
    protected void onDestroy() {
        stopPlay();
        super.onDestroy();
    }
}

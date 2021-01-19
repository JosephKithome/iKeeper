package com.sejjoh.ikeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class SplashScreen extends AppCompatActivity {
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AutoScrollPagerAdapter autoScrollPagerAdapter =
                new AutoScrollPagerAdapter(getSupportFragmentManager());
        AutoScrollViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(autoScrollPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // start auto scroll
        viewPager.startAutoScroll();
        // set auto scroll time in mili
        viewPager.setInterval(AUTO_SCROLL_THRESHOLD_IN_MILLI);
        // enable recycling using true
        viewPager.setCycle(true);
        TextView btnProceed =findViewById(R.id.proceed_btn);
        btnProceed.setOnClickListener(view -> {
            Intent next = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(next);
        });
    }
}
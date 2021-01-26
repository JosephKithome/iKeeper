package com.sejjoh.ikeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

        //add viewpager listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override
            public void onPageSelected(int position) {
                //open listing when on last page
                if (position == 2){
                   new Thread() {
                       @Override
                       public void run() {
                           super.run();
                           try {
                               sleep(AUTO_SCROLL_THRESHOLD_IN_MILLI);
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                           openKeeperListing();
                       }
                   }.start();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) { }
        });
        TextView btnProceed =findViewById(R.id.proceed_btn);
    }

    public void openKeeperListing() {
        Intent next = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(next);
    }
}
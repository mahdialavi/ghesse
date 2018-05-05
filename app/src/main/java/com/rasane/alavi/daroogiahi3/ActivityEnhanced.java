package com.rasane.alavi.daroogiahi3;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by alavi on 2/14/2018.
 */

public class ActivityEnhanced extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        G.ACTIVITY = this;
    }
}

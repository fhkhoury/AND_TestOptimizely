package com.fifty_five.optimizely;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.optimizely.ab.android.sdk.OptimizelyClient;

/**
 * Created by Julien Gil on 15/03/2017.
 */

public class VariationMainActivityA extends AppCompatActivity {

    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_a);

        myApplication = (MyApplication) getApplication();

        Button buttonConversion = (Button)findViewById(R.id.button_conversion);
        buttonConversion.setOnClickListener(onConversion);
    }

    View.OnClickListener onConversion = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OptimizelyClient optimizely = myApplication.getOptimizelyManager().getOptimizely();
            optimizely.track("myMetric", myApplication.getAnonUserId());
            Toast.makeText(getApplicationContext(), "Conversion main_a", Toast.LENGTH_LONG).show();
        }
    };
}
package com.fifty_five.optimizely;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.optimizely.ab.android.sdk.OptimizelyClient;
import com.optimizely.ab.android.sdk.OptimizelyManager;
import com.optimizely.ab.android.sdk.OptimizelyStartListener;
import com.optimizely.ab.config.Variation;


/**
 * Created by Julien Gil on 15/03/2017.
 */

public class SplashScreen extends AppCompatActivity {

    private OptimizelyManager optimizelyManager;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myApplication = (MyApplication) getApplication();
        optimizelyManager = myApplication.getOptimizelyManager();

        optimizelyManager.initialize(this, new OptimizelyStartListener() {

            @Override
            public void onStart(OptimizelyClient optimizely) {
                // this is the control variation, it will show if we are not able to determine which variation to bucket the user into
                Intent intent = new Intent(myApplication.getBaseContext(), MainActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }
}

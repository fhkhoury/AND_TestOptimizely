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

                // Activate user and start activity based on the variation we get.
                // You can pass in any string for the user ID. In this example we just use a convenience method to generate a random one.
                String userId = myApplication.getAnonUserId();
                Variation backgroundVariation = optimizelyManager.getOptimizely().activate("my_experiment", userId);

                // variation is nullable so we should check for null values
                if (backgroundVariation != null) {
                    // Show activity based on the variation the user got bucketed into
                    if (backgroundVariation.getKey().equals("main_a")) {
                        intent = new Intent(myApplication.getBaseContext(), VariationMainActivityA.class);
                    } else if (backgroundVariation.getKey().equals("main_b")) {
                        intent = new Intent(myApplication.getBaseContext(), VariationMainActivityB.class);
                    }
                }

                startActivity(intent);
            }
        });
        
    }
}

package com.fifty_five.optimizely;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.optimizely.ab.android.sdk.OptimizelyManager;
import com.optimizely.ab.config.Variation;

public class MainActivity extends AppCompatActivity {

    private OptimizelyManager optimizelyManager;
    private MyApplication myApplication;
    String textColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApplication = (MyApplication) getApplication();
        optimizelyManager = myApplication.getOptimizelyManager();

        // Activate user and start activity based on the variation we get.
        // You can pass in any string for the user ID. In this example we just use a convenience method to generate a random one.
        String userId = myApplication.getAnonUserId();
        Variation backgroundVariation = optimizelyManager.getOptimizely().activate("my_experiment", userId);

        // variation is nullable so we should check for null values
        if (backgroundVariation != null) {
            textColor = myApplication.getOptimizelyManager().getOptimizely().getVariableString(
                    "getTextColor",
                    myApplication.getAnonUserId(),
                    true
            );
        }

        TextView textActivity = (TextView) findViewById(R.id.textView);
        if (textColor != null) {
            textActivity.setTextColor(Color.parseColor(textColor));
        }

        Button buttonConversion = (Button)findViewById(R.id.button_conversion);
        buttonConversion.setOnClickListener(onConversion);
    }

    View.OnClickListener onConversion = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userId = myApplication.getAnonUserId();
            String key =  myApplication.getOptimizelyManager()
                    .getOptimizely()
                    .getVariation("my_experiment", userId)
                    .getKey();

            myApplication.getOptimizelyManager().getOptimizely().track("my_event", userId);
            Toast.makeText(getApplicationContext(), key+" conversion", Toast.LENGTH_LONG).show();
        }
    };
}

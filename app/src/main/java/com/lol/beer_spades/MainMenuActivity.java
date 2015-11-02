package com.lol.beer_spades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lol.beer_spades.game.GameActivity;

/**
 * Created by davidtownsend on 11/2/15.
 */
public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainmenu);

        setActivityLaunchingClickListener(R.id.imageButton1, GameActivity.class);
    }

    private void setActivityLaunchingClickListener(int buttonId, final Class<?> activityClass) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, activityClass));
            }
        });
    }
}

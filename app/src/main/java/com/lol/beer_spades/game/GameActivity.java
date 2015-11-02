package com.lol.beer_spades.game;

import android.app.Activity;
import android.os.Bundle;

import com.lol.beer_spades.R;

/**
 * Created by davidtownsend on 11/2/15.
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
    }
}

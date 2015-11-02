package com.lol.beer_spades.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lol.beer_spades.R;

/**
 * Created by davidtownsend on 11/2/15.
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        ImageButton image = (ImageButton) findViewById(R.id.cardOne);
        //image.setImageDrawable(context.getResources().getDrawable(R.drawable.testimage))
        image.setImageResource(R.drawable.king_of_spades);
    }
}

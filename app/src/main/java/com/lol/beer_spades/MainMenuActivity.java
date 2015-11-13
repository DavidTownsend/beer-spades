package com.lol.beer_spades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lol.beer_spades.game.GameActivity;
import com.lol.beer_spades.model.Player;
import com.lol.beer_spades.utils.FileUtilities;

import java.util.List;

/**
 * Created by davidtownsend on 11/2/15.
 */
public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mainmenu);

        setActivityLaunchingClickListener(R.id.newGame, GameActivity.class);
        //TODO still trying to figure this out
        //setResumeActivityLaunchingClickListener(R.id.continueGame, GameActivity.class);
    }

    private void setActivityLaunchingClickListener(int buttonId, final Class<?> activityClass) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, activityClass));
            }
        });
    }

    private void setResumeActivityLaunchingClickListener(int buttonId, final Class<?> activityClass) {
        findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Player> playersFromFile = FileUtilities.retrieveDataFromFile();
                Intent i = new Intent(getBaseContext(), activityClass);
                Bundle players = new Bundle();

                players.putSerializable("p1", playersFromFile.get(0));
                players.putSerializable("p2", playersFromFile.get(1));
                players.putSerializable("p3", playersFromFile.get(2));
                players.putSerializable("p4", playersFromFile.get(3));
                i.putExtras(players);
                startActivity(i);
            }
        });
    }
}

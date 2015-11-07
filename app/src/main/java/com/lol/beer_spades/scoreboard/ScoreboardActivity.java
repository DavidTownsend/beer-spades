package com.lol.beer_spades.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lol.beer_spades.R;
import com.lol.beer_spades.game.ActionsByAI;
import com.lol.beer_spades.game.BidType;
import com.lol.beer_spades.game.Card;
import com.lol.beer_spades.game.CardUtilities;
import com.lol.beer_spades.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Schimm on 11/7/2015.
 */
public class ScoreboardActivity extends Activity {
    private static final String TAG = ScoreboardActivity.class.getSimpleName();

    ActionsByAI aiAction;
    private List<Card> allCards;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<Card> roundCards;
    private BidType selectedBid;

    @Override
    // Initialization
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Bundle players = this.getIntent().getExtras();

        TextView p1Made = (TextView) findViewById(R.id.player1Made);
        Player player1 = (Player)players.getParcelable("p1");
        p1Made.setText(player1.getMade());

    }
}

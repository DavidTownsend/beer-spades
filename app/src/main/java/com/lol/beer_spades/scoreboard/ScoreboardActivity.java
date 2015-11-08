package com.lol.beer_spades.scoreboard;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.R;
import com.lol.beer_spades.game.ActionsByAI;
import com.lol.beer_spades.game.BidType;
import com.lol.beer_spades.game.Card;
import com.lol.beer_spades.game.CardUtilities;
import com.lol.beer_spades.player.Player;

import org.apache.commons.lang3.StringUtils;

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

        setContentView(R.layout.activity_scoreboard);

        Bundle players = this.getIntent().getExtras();
        Player player1 = (Player)players.getSerializable("p1");
        Player player2 = (Player)players.getSerializable("p2");
        Player player3 = (Player)players.getSerializable("p3");
        Player player4 = (Player)players.getSerializable("p4");

        assignPoints(player1);
        assignPoints(player2);
        assignPoints(player3);
        assignPoints(player4);

        TableRow p1ScoreboardRow = (TableRow) findViewById(R.id.player1Scoreboard);
        p1ScoreboardRow.addView(createTextView(player1.getPlayerName(), 0));
        p1ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player1.getMade()));
        p1ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player1.getBid()));
        p1ScoreboardRow.addView(createTextView(player1.getDisplayBags(), 0));
        p1ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player1.getRoundPoints()));
        p1ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player1.getTotalPoints()));

        TableRow p2ScoreboardRow = (TableRow) findViewById(R.id.player2Scoreboard);
        p2ScoreboardRow.addView(createTextView(player2.getPlayerName(), 0));
        p2ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player2.getMade()));
        p2ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player2.getBid()));
        p2ScoreboardRow.addView(createTextView(player2.getDisplayBags(), 0));
        p2ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player2.getRoundPoints()));
        p2ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player2.getTotalPoints()));

        TableRow p3ScoreboardRow = (TableRow) findViewById(R.id.player3Scoreboard);
        p3ScoreboardRow.addView(createTextView(player3.getPlayerName(), 0));
        p3ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player3.getMade()));
        p3ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player3.getBid()));
        p3ScoreboardRow.addView(createTextView(player3.getDisplayBags(), 0));
        p3ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player3.getRoundPoints()));
        p3ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player3.getTotalPoints()));

        TableRow p4ScoreboardRow = (TableRow) findViewById(R.id.player4Scoreboard);
        p4ScoreboardRow.addView(createTextView(player4.getPlayerName(), 0));
        p4ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player4.getMade()));
        p4ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player4.getBid()));
        p4ScoreboardRow.addView(createTextView(player4.getDisplayBags(), 0));
        p4ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player4.getRoundPoints()));
        p4ScoreboardRow.addView(createTextView(StringUtils.EMPTY, player4.getTotalPoints()));
    }

    private void assignPoints(Player player) {
        if (player.getMade() < player.getBid()) {
            player.setRoundBags(0);
            player.setRoundPoints(player.getBid() * -10);
        } else {
            player.setRoundBags(player.getMade() - player.getBid());
            player.setRoundPoints(player.getBid() * 10 + player. getRoundBags());
        }
        player.setTotalBags(player.getRoundBags() + player.getTotalBags());
        player.setTotalPoints(player.getRoundPoints() + player.getTotalPoints());
    }

    private TextView createTextView(String text, int intText) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(0, 100, 1f));
        textView.setTextColor(Color.parseColor(("#FF0000")));
        textView.setTextSize(20);
        if (StringUtils.isNotEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("" + intText);
        }

        return textView;
    }
}

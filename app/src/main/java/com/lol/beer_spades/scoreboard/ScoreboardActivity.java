package com.lol.beer_spades.scoreboard;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.R;
import com.lol.beer_spades.player.Player;

import org.apache.commons.lang3.StringUtils;


/**
 * Created by Schimm on 11/7/2015.
 */
public class ScoreboardActivity extends Activity {
    //TODO is this needed
    private static final String TAG = ScoreboardActivity.class.getSimpleName();

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
        addTableRowTextViews(p1ScoreboardRow, player1);

        TableRow p2ScoreboardRow = (TableRow) findViewById(R.id.player2Scoreboard);
        addTableRowTextViews(p2ScoreboardRow, player2);

        TableRow p3ScoreboardRow = (TableRow) findViewById(R.id.player3Scoreboard);
        addTableRowTextViews(p3ScoreboardRow, player3);

        TableRow p4ScoreboardRow = (TableRow) findViewById(R.id.player4Scoreboard);
        addTableRowTextViews(p4ScoreboardRow, player4);
    }

    private void addTableRowTextViews(TableRow scoreboardRow, Player player) {
        scoreboardRow.addView(createTextView(player.getPlayerName(), 0));
        scoreboardRow.addView(createTextView(StringUtils.EMPTY, player.getMade()));
        scoreboardRow.addView(createTextView(StringUtils.EMPTY, player.getBid()));
        scoreboardRow.addView(createTextView(player.getDisplayBags(), 0));
        scoreboardRow.addView(createTextView(StringUtils.EMPTY, player.getRoundPoints()));
        scoreboardRow.addView(createTextView(StringUtils.EMPTY, player.getTotalPoints()));
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

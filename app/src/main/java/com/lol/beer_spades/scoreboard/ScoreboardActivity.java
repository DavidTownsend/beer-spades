package com.lol.beer_spades.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.MainMenuActivity;
import com.lol.beer_spades.R;
import com.lol.beer_spades.game.GameActivity;
import com.lol.beer_spades.player.Player;

import org.apache.commons.lang3.StringUtils;


/**
 * Created by Schimm on 11/7/2015.
 */
public class ScoreboardActivity extends Activity {
    //TODO is this needed
    private static final String TAG = ScoreboardActivity.class.getSimpleName();
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @Override
    // Initialization
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_scoreboard);

        Bundle players = this.getIntent().getExtras();
        player1 = (Player) players.getSerializable("p1");
        player2 = (Player) players.getSerializable("p2");
        player3 = (Player) players.getSerializable("p3");
        player4 = (Player) players.getSerializable("p4");

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

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.scoreboardRelativeLayout);
        relativeLayout.setClickable(true);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), GameActivity.class);
                Bundle players = new Bundle();
                player1.clearBids();
                player2.clearBids();
                player3.clearBids();
                player4.clearBids();
                players.putSerializable("p1", player1);
                players.putSerializable("p2", player2);
                players.putSerializable("p3", player3);
                players.putSerializable("p4", player4);
                i.putExtras(players);
                startActivity(i);
            }
        });
    }

    private void addTableRowTextViews(TableRow scoreboardRow, Player player) {
        scoreboardRow.addView(createTextViewWithLeftPadding(player.getPlayerName(), 0));
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
            player.setRoundPoints(player.getBid() * 10 + player.getRoundBags());
        }
        player.setTotalBags(player.getRoundBags() + player.getTotalBags());
        player.setTotalPoints(player.getRoundPoints() + player.getTotalPoints());
    }

    private TextView createTextView(String text, int intText) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(0, 100, 1f));
        textView.setTextColor(Color.parseColor(("#FF0000")));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        if (StringUtils.isNotEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setText("" + intText);
        }
        return textView;
    }

    private TextView createTextViewWithLeftPadding(String text, int intText) {
        TextView tv = createTextView(text, intText);
        tv.setPadding(10, 0, 0, 0);
        return tv;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ScoreboardActivity.this, MainMenuActivity.class));
        finish();
    }
}

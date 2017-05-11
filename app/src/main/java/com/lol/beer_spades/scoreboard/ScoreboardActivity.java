package com.lol.beer_spades.scoreboard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.MainMenuActivity;
import com.lol.beer_spades.R;
import com.lol.beer_spades.bid.BidActivity;
import com.lol.beer_spades.gameover.GameOverActivity;
import com.lol.beer_spades.model.Player;

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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        if (isGameOver()) {
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getBaseContext(), GameOverActivity.class);
                    Bundle players = new Bundle();
                    players.putSerializable("p1", player1);
                    players.putSerializable("p2", player2);
                    players.putSerializable("p3", player3);
                    players.putSerializable("p4", player4);
                    i.putExtras(players);
                    startActivity(i);
                }
            });
        }
        else {
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getBaseContext(), BidActivity.class);
                    Bundle players = new Bundle();
                    player1.startNewRound();
                    player2.startNewRound();
                    player3.startNewRound();
                    player4.startNewRound();
                    players.putSerializable("p1", player1);
                    players.putSerializable("p2", player2);
                    players.putSerializable("p3", player3);
                    players.putSerializable("p4", player4);
                    i.putExtras(players);
                    startActivity(i);
                }
            });
        }
    }

    private boolean isGameOver() {
        return (player1.enoughPointsToWin() || player2.enoughPointsToWin() || player3.enoughPointsToWin() || player4.enoughPointsToWin());
    }

    private void addTableRowTextViews(TableRow scoreboardRow, Player player) {
        scoreboardRow.addView(createTextViewWithLeftPadding(player.getPlayerName()));
        scoreboardRow.addView(createTextView("" + player.getMade()));
        scoreboardRow.addView(createTextView(player.getBid().getDisplayValue()));
        scoreboardRow.addView(createTextView(player.getDisplayBags()));
        scoreboardRow.addView(createTextView("" + player.getRoundPoints()));
        scoreboardRow.addView(createTextView("" + player.getTotalPoints()));
    }

    private void assignPoints(Player player) {
        if (player.bidNil()) {
            if (player.getMade() != 0) {
                player.setRoundPoints(-100);
                player.setRoundBags(player.getMade());
            }
            else {
                player.setRoundPoints(100);
                player.setRoundBags(0);
            }
        }
        else if (player.bidDoubleNil()) {
            if (player.getMade() != 0) {
                player.setRoundPoints(-200);
                player.setRoundBags(player.getMade());
            }
            else {
                player.setRoundPoints(200);
                player.setRoundBags(0);
            }
        }
        else if (player.getMade() < player.getBid().getValue()) {
            player.setRoundBags(0);
            player.setRoundPoints(player.getBid().getValue() * -10);
        } else {
            player.setRoundBags(player.getMade() - player.getBid().getValue());
            player.setRoundPoints(player.getBid().getValue() * 10 + player.getRoundBags());
        }
        player.setTotalBags(player.getRoundBags() + player.getTotalBags());
        player.setTotalPoints(player.getRoundPoints() + player.getTotalPoints());

        if (player.getTotalBags() >= 10) {
            player.setTotalPoints(player.getTotalPoints() - 100);
            player.setTotalBags(player.getTotalBags() - 10);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(0, 100, 1f));
        textView.setTextColor(Color.parseColor(("#FF0000")));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(text);
        return textView;
    }

    private TextView createTextViewWithLeftPadding(String text) {
        TextView tv = createTextView(text);
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

package com.lol.beer_spades.gameover;

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
import com.lol.beer_spades.model.Player;
import com.lol.beer_spades.scoreboard.ScoreboardActivity;

/**
 * Created by 21jiggawatts on 5/10/2017.
 */

public class GameOverActivity extends Activity {

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

        setContentView(R.layout.activity_gameover);

        Bundle players = this.getIntent().getExtras();
        player1 = (Player) players.getSerializable("p1");
        player2 = (Player) players.getSerializable("p2");
        player3 = (Player) players.getSerializable("p3");
        player4 = (Player) players.getSerializable("p4");

        Player winner = findWinner();

        TableRow winnerRow = (TableRow) findViewById(R.id.gameOverRow);

        winnerRow.addView(createTextView(winner.getPlayerName()));

        player1.gameOver();
        player2.gameOver();
        player3.gameOver();
        player4.gameOver();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.gameOverRelativeLayout);
        relativeLayout.setClickable(true);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), MainMenuActivity.class);
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

    private Player findWinner() {
        Player winner = new Player();
        if (player1.enoughPointsToWin()) {
            winner = player1;
        }
        if (player2.enoughPointsToWin() && player2.getTotalPoints() > winner.getTotalPoints()) {
            winner = player2;
        }
        if (player3.enoughPointsToWin() && player3.getTotalPoints() > winner.getTotalPoints()) {
            winner = player3;
        }
        if (player4.enoughPointsToWin() && player4.getTotalPoints() > winner.getTotalPoints()) {
            winner = player4;
        }
        return winner;
    }

    private TextView createTextView(String winnerName) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new TableRow.LayoutParams(0, 100, 1f));
        textView.setTextColor(Color.parseColor(("#FF0000")));
        textView.setTextSize(20);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText("Congratulations " + winnerName);
        return textView;
    }
}

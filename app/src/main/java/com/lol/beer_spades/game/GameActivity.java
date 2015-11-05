package com.lol.beer_spades.game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lol.beer_spades.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidtownsend on 11/2/15.
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        List<Card> randomCards = generateCards();
        List<Card> player1 = new ArrayList<>();
        List<Card> player2 = new ArrayList<>();
        List<Card> player3 = new ArrayList<>();
        List<Card> player4 = new ArrayList<>();

        for (int i = 0; i < 52; i++) {
            player1.add(randomCards.get(i++));
            player2.add(randomCards.get(i++));
            player3.add(randomCards.get(i++));
            player4.add(randomCards.get(i));
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.fullscreen_content_controls);

        Collections.sort(player1);

        for(Card card : player1){
            int resID = getResources().getIdentifier(card.toString(), "drawable", getPackageName());
            createNewImageView(resID, linearLayout);
        }
    }

    private void createNewImageView(int resId, LinearLayout linearLayout){
        ImageView imageView = new ImageView(this);

        imageView.setImageResource(resId);
        imageView.setMaxHeight(265);
        imageView.setMaxWidth(265);
        imageView.setAdjustViewBounds(true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);

        linearLayout.addView(imageView);
    }

    private List<Card> generateCards() {
        List<Card> deck = new ArrayList<Card>();
        List<SuiteType> suites = retrieveSuite();

        for(int j = 0; j < 4; j++) {
            for (int i = 2; i <= 14; i++) {
                deck.add(new Card(suites.get(j), i));
            }
        }

        Collections.shuffle(deck);

        return deck;
    }

    private List<SuiteType> retrieveSuite(){
        List<SuiteType> suite = new ArrayList<SuiteType>();

        suite.add(SuiteType.clubs);
        suite.add(SuiteType.hearts);
        suite.add(SuiteType.spades);
        suite.add(SuiteType.diamonds);

        return suite;
    }
}

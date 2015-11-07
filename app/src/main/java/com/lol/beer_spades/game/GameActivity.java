package com.lol.beer_spades.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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

    private List<Card> allCards;
    private List<Card> player1;
    private List<Card> player2;
    private List<Card> player3;
    private List<Card> player4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        allCards = generateCards();

        player1 = new ArrayList<>();
        player2 = new ArrayList<>();
        player3 = new ArrayList<>();
        player4 = new ArrayList<>();

        Collections.shuffle(allCards);
        for (int i = 0; i < 52; i++) {
            player1.add(allCards.get(i++));
            player2.add(allCards.get(i++));
            player3.add(allCards.get(i++));
            player4.add(allCards.get(i));
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playing_area);

        Collections.sort(player1);

        for(Card card : player1){
            int resID = getResources().getIdentifier(card.toString(), "drawable", getPackageName());
            card.setResourceId(resID);
            createNewImageView(resID, linearLayout, card.getId());
        }
    }

    private void createNewImageView(int resId, LinearLayout linearLayout, int cardId){
        ImageView imageView = new ImageView(this);

        imageView.setImageResource(resId);
        imageView.setMaxHeight(265);
        imageView.setMaxWidth(265);
        imageView.setAdjustViewBounds(true);
        imageView.setId(cardId);
        //imageView.
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //lp.topMargin(10);
        imageView.setLayoutParams(lp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playCard(view);;
            }
        });

        linearLayout.addView(imageView);
    }

    private List<Card> generateCards() {
        List<Card> deck = new ArrayList<Card>();
        List<SuiteType> suites = retrieveSuite();
        Integer id = 0;

        for(int j = 0; j < 4; j++) {
            for (int i = 2; i <= 14; i++) {
                deck.add(new Card(id, suites.get(j), i));
                id ++;
            }
        }

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

    private void playCard(View view){
        Card card = getCard(view.getId());
        ImageView imageView = new ImageView(this);

        imageView.setImageResource(card.getResourceId());
        imageView.setMaxHeight(265);
        imageView.setMaxWidth(265);
        imageView.setAdjustViewBounds(true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.playing_area);
        linearLayout.addView(imageView);
    }

    private Card getCard(int id){
        for(Card card : allCards){
            if(id == card.getId()){
                return card;
            }
        }

        return null;
    }
}

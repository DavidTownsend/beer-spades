package com.lol.beer_spades.game;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtownsend on 11/6/15.
 */
public class CardUtilities {

    public static List<Card> generateCards() {
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

    private static List<SuiteType> retrieveSuite(){
        List<SuiteType> suite = new ArrayList<SuiteType>();

        suite.add(SuiteType.clubs);
        suite.add(SuiteType.hearts);
        suite.add(SuiteType.spades);
        suite.add(SuiteType.diamonds);

        return suite;
    }

    public static void setupBasicImageProperties(ImageView imageView, Card card, Integer x_position, Integer y_position){
        imageView.setImageResource(card.getResourceId());
        imageView.setMaxHeight(165);
        imageView.setMaxWidth(165);
        imageView.setAdjustViewBounds(true);
        imageView.setId(card.getId());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(null != x_position && null != y_position) {
            lp.topMargin = y_position;
            lp.leftMargin = x_position;
        }

        imageView.setLayoutParams(lp);
    }

    public static Card getCard(List<Card> allCards, int id){
        for(Card card : allCards){
            if(id == card.getId()){
                return card;
            }
        }

        return null;
    }
}

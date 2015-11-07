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
        List<SuitType> suits = retrieveSuit();
        Integer id = 0;

        for(int j = 0; j < 4; j++) {
            for (int i = 2; i <= 14; i++) {
                deck.add(new Card(id, suits.get(j), i));
                id ++;
            }
        }

        return deck;
    }

    private static List<SuitType> retrieveSuit(){
        List<SuitType> suit = new ArrayList<SuitType>();

        suit.add(SuitType.clubs);
        suit.add(SuitType.hearts);
        suit.add(SuitType.spades);
        suit.add(SuitType.diamonds);

        return suit;
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

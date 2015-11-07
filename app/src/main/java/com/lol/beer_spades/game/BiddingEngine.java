package com.lol.beer_spades.game;

import java.util.List;

/**
 * Created by davidtownsend on 11/7/15.
 */
public class BiddingEngine {
    public Integer calculateBid(List<Card> cards){
        Double bid = 0.0;

        for(Card card : cards){
            if(card.getCardNumber() == 10 || card.getCardNumber() == 11){
                bid += .25;
            } else if (card.getCardNumber() == 12){
                bid += .5;
            } else if (card.getCardNumber() == 13){
                bid += .75;
            } else if (card.getCardNumber() == 14) {
                bid += 1;
            }
        }

        return (int) Math.round(bid);

    }
}

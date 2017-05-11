package com.lol.beer_spades.ai;

import com.lol.beer_spades.model.BidType;
import com.lol.beer_spades.model.Card;
import com.lol.beer_spades.model.Player;

import java.util.List;

/**
 * Created by davidtownsend on 11/7/15.
 */
public class BiddingEngine {
    private static BidType calculateBid(List<Card> cards){
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

        return new BidType((int)Math.round(bid), null);
    }

    public static void setBid(Player player){
        player.setBid(calculateBid(player.getCards()));
    }
}

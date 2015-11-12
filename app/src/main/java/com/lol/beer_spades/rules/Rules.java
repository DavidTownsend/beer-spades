package com.lol.beer_spades.rules;

import com.lol.beer_spades.game.Card;
import com.lol.beer_spades.game.SuitType;
import com.lol.beer_spades.player.Player;

import java.util.List;

/**
 * Created by davidtownsend on 11/11/15.
 */
public class Rules {

    public static void cardsAllowedToPlay(List<Card> cards, Enum<SuitType> suitType, boolean ableToPlaySpades){
        boolean hasSuit = false;
        boolean onlyHasSpades = true;

        if (suitType != null){
            for (Card card : cards){
                if(suitType.equals(card.getSuitType())){
                    hasSuit = true;
                    break;
                }
            }
        }

        for (Card card: cards){
            if(!SuitType.spades.equals(card.getSuitType())){
                onlyHasSpades = false;
                break;
            }
        }

        if (hasSuit) {
            for (Card card : cards){
                if(suitType.equals(card.getSuitType())){
                    card.setAllowedToBePlayed(true);
                }else{
                    card.setAllowedToBePlayed(false);
                }
            }
        } else if (cards.size() == 13){
            for (Card card : cards){
                if(SuitType.spades.equals(card.getSuitType()) && !onlyHasSpades){
                    card.setAllowedToBePlayed(false);
                } else {
                    card.setAllowedToBePlayed(true);
                }
            }
        } else if (suitType != null){
            for (Card card : cards){
                card.setAllowedToBePlayed(true);
            }
        } else {
            for (Card card : cards){
                if(!ableToPlaySpades && SuitType.spades.equals(card.getSuitType()) && !onlyHasSpades){
                    card.setAllowedToBePlayed(false);
                } else {
                    card.setAllowedToBePlayed(true);
                }
            }
        }
    }
}

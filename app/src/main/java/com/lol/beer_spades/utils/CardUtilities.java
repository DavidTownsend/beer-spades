package com.lol.beer_spades.utils;

import com.lol.beer_spades.game.Card;
import com.lol.beer_spades.game.SuitType;
import com.lol.beer_spades.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidtownsend on 11/6/15.
 */
public class CardUtilities {

    public static List<Card> generateCards() {
        List<Card> deck = new ArrayList<>();
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
        List<SuitType> suit = new ArrayList<>();

        suit.add(SuitType.clubs);
        suit.add(SuitType.hearts);
        suit.add(SuitType.spades);
        suit.add(SuitType.diamonds);

        return suit;
    }

    public static Card getCard(List<Card> allCards, int id){
        for(Card card : allCards){
            if(id == card.getId()){
                return card;
            }
        }

        return null;
    }

    public static Card getSelectCard(List<Card> cards){
        for(Card card : cards){
            if(card.isSelected()){
                return card;
            }
        }

        return null;
    }

    public static void dealCards(Player player1, Player player2, Player player3, Player player4){
        List<Card> allCards = CardUtilities.generateCards();
        Collections.shuffle(allCards);

        for (int i = 0; i < 52; i++) {
            Card card1 = allCards.get(i++);
            card1.setPlayerName(player1.getPlayerName());
            player1.getCards().add(card1);

            Card card2 = allCards.get(i++);
            card2.setPlayerName(player2.getPlayerName());
            player2.getCards().add(card2);

            Card card3 = allCards.get(i++);
            card3.setPlayerName(player3.getPlayerName());
            player3.getCards().add(card3);

            Card card4 = allCards.get(i);
            card4.setPlayerName(player4.getPlayerName());
            player4.getCards().add(card4);
        }

        Collections.sort(player1.getCards());
        Collections.sort(player2.getCards());
        Collections.sort(player3.getCards());
        Collections.sort(player4.getCards());
    }
}

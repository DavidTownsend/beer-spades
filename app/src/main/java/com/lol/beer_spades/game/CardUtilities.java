package com.lol.beer_spades.game;

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
}

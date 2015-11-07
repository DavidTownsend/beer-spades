package com.lol.beer_spades.player;

import com.lol.beer_spades.game.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtownsend on 11/7/15.
 */
public class Player {
    private List<Card> cards;
    private Integer bid;
    private Integer made;
    private String playerName;

    public Player (){
        cards = new ArrayList<>();
        bid = 0;
        made = 0;
    }

    public Player (String playerName) {
        this();
        this.playerName = playerName;
    }

    public void increaseMade() {
        made += 1;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Integer getMade() {
        return made;
    }

    public void setMade(Integer made) {
        this.made = made;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}

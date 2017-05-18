package com.lol.beer_spades.model;

import com.lol.beer_spades.model.Card;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtownsend on 11/7/15.
 */
public class Player implements Serializable {
    private static final int POINTS_TO_WIN = 100;
    private List<Card> cards;
    private Integer bid = 0;
    private Integer made = 0;
    private String playerName;
    private Integer roundPoints = 0;
    private Integer totalPoints = 0;
    private Integer roundBags = 0;
    private Integer totalBags = 0;

    public Player() {
        super();
    }

    public Player (String playerName) {
        cards = new ArrayList<>();
        bid = 0;
        made = 0;
        this.playerName = playerName;
    }

    public void gameOver() {
        clearBids();
        roundPoints = 0;
        roundBags = 0;
        totalBags = 0;
        totalPoints = 0;
    }

    public boolean enoughPointsToWin() {
        return (totalPoints > POINTS_TO_WIN);
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

    public Integer getMade() {
        return made;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getRoundPoints() {
        return roundPoints;
    }

    public void setRoundPoints(Integer roundPoints) {
        this.roundPoints = roundPoints;
    }

    public Integer getTotalBags() {
        return totalBags;
    }

    public void setTotalBags(Integer totalBags) {
        this.totalBags = totalBags;
    }

    public Integer getRoundBags() {
        return roundBags;
    }

    public void setRoundBags(Integer roundBags) {
        this.roundBags = roundBags;
    }

    public String getDisplayBags() {
        return "(" + roundBags + ")" + totalBags;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isHandOver(){
        return cards.size() == 0;
    }

    public void clearBids(){
        bid = 0;
        made = 0;
    }
}

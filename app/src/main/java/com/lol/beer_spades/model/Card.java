package com.lol.beer_spades.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by davidtownsend on 11/5/15.
 */
public class Card implements Comparable<Card>{
    private Integer id;
    @SerializedName("suitType")
    private Enum suitType;
    private Integer cardNumber;
    private Integer resourceId;
    private boolean selected;
    private boolean allowedToBePlayed;
    private String playerName;

    public Card (Integer id, Enum suitType, Integer cardNumber){
        this.suitType = suitType;
        this.cardNumber = cardNumber;
        this.id = id;
        allowedToBePlayed = true;
    }

    public String toString(){
        return suitType.toString() + cardNumber.toString();
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Enum getSuitType() {
        return suitType;
    }

    public void setSuitType(Enum suitType) {
        this.suitType = suitType;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAllowedToBePlayed() {
        return allowedToBePlayed;
    }

    public void setAllowedToBePlayed(boolean allowedToBePlayed) {
        this.allowedToBePlayed = allowedToBePlayed;
    }


    @Override
    public int compareTo(Card o) {
        // -3 to 3, 0 if the same suit
        int rankCom = suitType.compareTo(o.suitType);
        // if not same suit return rankCom
        // else compare cardNumber
        return rankCom != 0 ? rankCom : cardNumber.compareTo(o.cardNumber);
    }

    public static Card pickWinner4(List<Card> roundCards) {
        if (roundCards == null || roundCards.size() == 0) {
            return null;
        }
        Card winningCard = roundCards.get(0);
        for (int i=1; i<4; i++) {
            winningCard = pickWinner2(winningCard, roundCards.get(i));
        }
        return winningCard;
    }

    private static Card pickWinner2(Card winningCard, Card card2) {
        if (card2.getSuitType() == winningCard.getSuitType()) {
            if (winningCard.getCardNumber().compareTo(card2.getCardNumber()) < 0) {
                winningCard = card2;
            }
        } else if (card2.getSuitType() == SuitType.spades) {
            winningCard = card2;
        }
        return winningCard;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}

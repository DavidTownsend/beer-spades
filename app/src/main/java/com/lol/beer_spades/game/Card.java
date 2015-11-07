package com.lol.beer_spades.game;

import java.util.Objects;

/**
 * Created by davidtownsend on 11/5/15.
 */
public class Card implements Comparable<Card>{
    private Integer id;
    private Enum suitType;
    private Integer cardNumber;
    private Integer resourceId;
    private boolean selected;

    public Card (Integer id, Enum suitType, Integer cardNumber){
        this.suitType = suitType;
        this.cardNumber = cardNumber;
        this.id = id;
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

    @Override
    public int compareTo(Card o) {
        int rankCom = suitType.compareTo(o.suitType);
        return rankCom != 0 ? rankCom : o.cardNumber.compareTo(cardNumber);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

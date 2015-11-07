package com.lol.beer_spades.game;

import java.util.Objects;

/**
 * Created by davidtownsend on 11/5/15.
 */
public class Card implements Comparable<Card>{
    private Integer id;
    private Enum suiteType;
    private Integer cardNumber;
    private Integer resourceId;

    public Card (Integer id, Enum suiteType, Integer cardNumber){
        this.suiteType = suiteType;
        this.cardNumber = cardNumber;
        this.id = id;
    }

    public String toString(){
        return suiteType.toString() + cardNumber.toString();
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Enum getSuiteType() {
        return suiteType;
    }

    public void setSuiteType(Enum suiteType) {
        this.suiteType = suiteType;
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
        int rankCom = suiteType.compareTo(o.suiteType);
        return rankCom != 0 ? rankCom : o.cardNumber.compareTo(cardNumber);
    }
}

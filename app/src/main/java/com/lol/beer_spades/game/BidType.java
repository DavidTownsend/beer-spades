package com.lol.beer_spades.game;

/**
 * Created by Schimm on 11/7/2015.
 */
public class BidType {
    public static final BidType NIL = new BidType(-1, "nilBid");
    public static final BidType DOUBLENIL = new BidType(-10, "doubleNilBid");
    public static final BidType ZERO = new BidType(0, "zeroBid");
    public static final BidType ONE = new BidType(1, "oneBid");
    public static final BidType TWO = new BidType(2, "twoBid");
    public static final BidType THREE = new BidType(3, "threeBid");
    public static final BidType FOUR = new BidType(4, "fourBid");
    public static final BidType FIVE = new BidType(5, "fiveBid");
    public static final BidType SIX = new BidType(6, "sixBid");
    public static final BidType SEVEN = new BidType(7, "sevenBid");
    public static final BidType EIGHT = new BidType(8, "eightBid");
    public static final BidType NINE = new BidType(9, "nineBid");
    public static final BidType TEN = new BidType(10, "tenBid");
    public static final BidType ELEVEN = new BidType(11, "elevenBid");
    public static final BidType TWELVE = new BidType(12, "twelveBid");
    public static final BidType THIRTEEN = new BidType(13, "thirteenBid");

    private int value;
    private String buttonId;

    public BidType(int value, String buttonId) {
        this.value = value;
        this.buttonId = buttonId;
    }

    public static BidType findBidType(String buttonText) {
        if (buttonText == "" || buttonText == null) {
            return null;
        }

        switch (buttonText) {
            case "Nil":
                return NIL;
            case "Dbl. Nil":
                return DOUBLENIL;
            case "0":
                return ZERO;
            case "1":
                return ONE;
            case "2":
                return TWO;
            case "3":
                return THREE;
            case "4":
                return FOUR;
            case "5":
                return FIVE;
            case "6":
                return SIX;
            case "7":
                return SEVEN;
            case "8":
                return EIGHT;
            case "9":
                return NINE;
            case "10":
                return TEN;
            case "11":
                return ELEVEN;
            case "12":
                return TWELVE;
            case "13":
                return THIRTEEN;
            default:
                return null;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getButtonId() {
        return buttonId;
    }

    public void setButtonId(String buttonId) {
        this.buttonId = buttonId;
    }

}

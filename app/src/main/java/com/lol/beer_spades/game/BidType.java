package com.lol.beer_spades.game;

/**
 * Created by Schimm on 11/7/2015.
 */
public class BidType {
    private static final BidType NIL = new BidType(-1, "nilBid");
    private static final BidType DOUBLENIL = new BidType(-10, "doubleNilBid");
    private static final BidType ZERO = new BidType(0, "zeroBid");
    private static final BidType ONE = new BidType(1, "oneBid");
    private static final BidType TWO = new BidType(2, "twoBid");
    private static final BidType THREE = new BidType(3, "threeBid");
    private static final BidType FOUR = new BidType(4, "fourBid");
    private static final BidType FIVE = new BidType(5, "fiveBid");
    private static final BidType SIX = new BidType(6, "sixBid");
    private static final BidType SEVEN = new BidType(7, "sevenBid");
    private static final BidType EIGHT = new BidType(8, "eightBid");
    private static final BidType NINE = new BidType(9, "nineBid");
    private static final BidType TEN = new BidType(10, "tenBid");
    private static final BidType ELEVEN = new BidType(11, "elevenBid");
    private static final BidType TWELVE = new BidType(12, "twelveBid");
    private static final BidType THIRTEEN = new BidType(13, "thirteenBid");

    private int value;
    private String buttonId;

    public BidType(int value, String buttonId) {
        this.value = value;
        this.buttonId = buttonId;
    }

    public static BidType findBidType(String buttonText) {
        if (buttonText.equals("") || buttonText == null) {
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
    public String getButtonId() {
        return buttonId;
    }
}

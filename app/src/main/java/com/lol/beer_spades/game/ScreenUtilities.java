package com.lol.beer_spades.game;

import android.graphics.Point;
import android.view.Display;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by D on 11/7/2015.
 */
public class ScreenUtilities {
    private final static double HAND_AREA_MULTIPLIER = .23;
    private final static double CARD_SIZE_MULTIPLIER = .09;
    private final static double SELECTED_CARD_Y_MULTIPLIER = .07;
    private final static double PLAY_AREA_X_MULTIPLIER = .3;
    private final static double PLAY_AREA_Y_MULTIPLIER = .5;

    /*RelativeLayout relativeLayout
    renderCard(card, 125, 200, relativeLayout);
    playAICards(player2, 0, 100, relativeLayout);
    playAICards(player3, 125, 0, relativeLayout);
    playAICards(player4, 250, 100, relativeLayout);*/

    private static int getScreenHeight(Display display) {
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    private static int getScreenWidth(Display display) {
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getHandAreaHeight(Display display) {
        return (int) (getScreenHeight(display) * HAND_AREA_MULTIPLIER);
    }

    public static int getCardHeight(Display display) {
        return (int) (getScreenWidth(display) * CARD_SIZE_MULTIPLIER);
    }

    public static int getSelectedCardYIncrease(Display display) {
        return (int) (getScreenHeight(display) * SELECTED_CARD_Y_MULTIPLIER);
    }

    public static int getPlayAreaHeight(Display display) {
        return (int) (getScreenHeight(display) * PLAY_AREA_Y_MULTIPLIER);
    }

    public static int getPlayAreaWidth(Display display) {
        return (int) (getScreenWidth(display) * PLAY_AREA_X_MULTIPLIER);
    }

    public static int getPlayer1XCoordinate(RelativeLayout playArea) {
        return (int) (playArea.getX() / 3.5);
    }

    public static int getPlayer1YCoordinate(RelativeLayout playArea) {
        return (int) playArea.getY();
    }

    public static int getPlayer2XCoordinate(RelativeLayout playArea) {
        return (int) 0;
    }

    public static int getPlayer2YCoordinate(RelativeLayout playArea) {
        return (int) (playArea.getY() / 2.5);
    }

    public static int getPlayer3XCoordinate(RelativeLayout playArea) {
        return (int) (playArea.getX() / 3.5);
    }

    public static int getPlayer3YCoordinate(RelativeLayout playArea) {
        return (int) 0;
    }

    public static int getPlayer4XCoordinate(RelativeLayout playArea) {
        return (int) (playArea.getX() / 1.8);
    }

    public static int getPlayer4YCoordinate(RelativeLayout playArea) {
        return (int) (playArea.getY() / 2.5);
    }
}

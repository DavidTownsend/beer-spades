package com.lol.beer_spades.render;

import android.app.Activity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lol.beer_spades.R;
import com.lol.beer_spades.game.Card;
import com.lol.beer_spades.utils.CardUtilities;
import com.lol.beer_spades.utils.ScreenUtilities;

import java.util.List;

/**
 * Created by davidtownsend on 11/11/15.
 */
public abstract class BaseRenderActivity extends Activity{
    public static final String DRAWABLE = "drawable";
    public static final String ID = "id";
    protected Display display;

    protected void configureHandArea() {
        LinearLayout handArea = (LinearLayout) findViewById(R.id.hand_area);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtilities.getHandAreaHeight(display));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        handArea.setLayoutParams(lp);
    }

    protected void renderCard(Card card, Integer x_position, Integer y_position, RelativeLayout playingArea){
        ImageView imageView = new ImageView(this);
        setupImageViewFromCard(card, imageView, x_position, y_position);
        playingArea.addView(imageView);
    }

    // Hide the card's image
    protected void removeCardFromView(Card card) {
        ImageView imageView = (ImageView) findViewById(card.getId());
        ((ViewGroup)imageView.getParent()).removeView(imageView);
    }

    protected void setupImageViewFromCard(Card card, ImageView imageView, Integer x_position, Integer y_position){
        imageView.setImageBitmap(CardUtilities.decodeSampledBitmapFromResource(getResources(), card.getResourceId(), ScreenUtilities.getCardHeight(display), ScreenUtilities.getCardHeight(display)));
        imageView.setMaxHeight(ScreenUtilities.getCardHeight(display));
        imageView.setAdjustViewBounds(true);
        imageView.setId(card.getId());
        imageView.setPadding(3, 0, 3, 0);
        imageView.setLayoutParams(setupLayoutParams(x_position, y_position));
    }

    private RelativeLayout.LayoutParams setupLayoutParams(Integer x_position, Integer y_position){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(null != x_position && null != y_position) {
            lp.topMargin = y_position;
            lp.leftMargin = x_position;
        }

        return lp;
    }

    protected int getResourceId(String resourceName, String packageName){
        return getResources().getIdentifier(resourceName, packageName, getPackageName());
    }

    // On-click for the card images. Increase or decrease the card's y value to show as selected
    protected void selectHumanPlayerCard(View view, RelativeLayout relativeLayout, List<Card> cards) {
        Card selectedCard = CardUtilities.getCard(cards, view.getId());
        ImageView imageView = (ImageView) findViewById(view.getId());

        // If this card is already selected - deselect it
        if (selectedCard.isSelected()) {
            imageView.setY(imageView.getY() + ScreenUtilities.getSelectedCardYIncrease(display));
            selectedCard.setSelected(false);
            relativeLayout.setClickable(false);
            return;
        }

        // Another card is already selected
        if (CardUtilities.getSelectCard(cards) != null) {
            return;
        }

        selectedCard.setSelected(true);
        imageView.setY(imageView.getY() - ScreenUtilities.getSelectedCardYIncrease(display));
        relativeLayout.setClickable(true);
    }
}

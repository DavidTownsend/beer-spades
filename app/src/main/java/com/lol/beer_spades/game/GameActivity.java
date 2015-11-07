package com.lol.beer_spades.game;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by davidtownsend on 11/2/15.
 */
public class GameActivity extends Activity {

    private List<Card> allCards;
    private List<Card> player1;
    private List<Card> player2;
    private List<Card> player3;
    private List<Card> player4;
    private List<Card> roundCards;
    private BidType selectedBid;

    @Override
    // Initialization
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game);

        allCards = CardUtilities.generateCards();

        player1 = new ArrayList<>();
        player2 = new ArrayList<>();
        player3 = new ArrayList<>();
        player4 = new ArrayList<>();
        roundCards = new ArrayList<>();

        Collections.shuffle(allCards);
        for (int i = 0; i < 52; i++) {
            player1.add(allCards.get(i++));
            player2.add(allCards.get(i++));
            player3.add(allCards.get(i++));
            player4.add(allCards.get(i));
        }

        Collections.sort(player1);

        drawInitialHand();
        configurePlayingArea();

        setupBidTable();
    }

    // Configure the center/playing area
    private void configurePlayingArea() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.playing_area);
        relativeLayout.setClickable(true);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card selectedCard = getSelectedCard();

                // If a card was selected - play it
                if (selectedCard != null) {
                    playCard(selectedCard);
                    // If there are cards in the center
                } else if (roundCards != null && roundCards.size() != 0) {
                    collectRoundCards(view);
                }
            }
        });
    }

    // Create a single card image
    private void createNewImageView(int resId, LinearLayout linearLayout, int cardId) {
        ImageView imageView = new ImageView(this);

        imageView.setImageResource(resId);
        imageView.setMaxHeight(175);
        imageView.setMaxWidth(175);
        imageView.setAdjustViewBounds(true);
        imageView.setId(cardId);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelativeLayout playingArea = (RelativeLayout) findViewById(R.id.playing_area);
                TableLayout bidArea = (TableLayout) findViewById(R.id.bidTable);

                // No cards in the center and bid selection isn't viewable
                if ((playingArea.getChildCount() == 0 || playingArea.getVisibility() == View.GONE)
                        & bidArea.getVisibility() == View.GONE) {
                    selectCard(view);
                }
            }
        });

        linearLayout.addView(imageView);
    }

    // On-click for the card images. Increase or decrease the card's y value to show as selected
    private void selectCard(View view) {
        Card selectedCard = CardUtilities.getCard(allCards, view.getId());
        ImageView imageView = (ImageView) findViewById(view.getId());

        // If this card is already selected - deselect it
        if (selectedCard.isSelected()) {
            imageView.setY(imageView.getY() + 60);
            selectedCard.setSelected(false);
            return;
        }

        // Another card is already selected
        if (getSelectedCard() != null) {
            return;
        }

        selectedCard.setSelected(true);
        imageView.setY(imageView.getY() - 60);
    }

    // Get the card from player1 that is selected
    private Card getSelectedCard() {
        for (Card card : player1) {
            if (card.isSelected()) {
                return card;
            }
        }

        return null;
    }

    // Add the player1 card to the roundCards and playing area
    private void playCard(Card card) {
        roundCards.add(card);
        removeCardView(card);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.playing_area);

        renderCard(card, 125, 200, relativeLayout);
        playAICards(player2, 0, 100, relativeLayout);
        playAICards(player3, 125, 0, relativeLayout);
        playAICards(player4, 250, 100, relativeLayout);

        player1.remove(card);

        TextView p1_tricks = (TextView) findViewById(R.id.p1_tricks);
        p1_tricks.setText("P1 \n 0/4");

        TextView p2_tricks = (TextView) findViewById(R.id.p2_tricks);
        p2_tricks.setText("P2 \n 0/4");

        TextView p3_tricks = (TextView) findViewById(R.id.p3_tricks);
        p3_tricks.setText("P3 \n 0/4");

        TextView p4_tricks = (TextView) findViewById(R.id.p4_tricks);
        p4_tricks.setText("P4 \n 0/4");
    }

    // Add a random AI card to the roundCards and playing area
    private void playAICards(List<Card> playerHand, int x_position, int y_position, RelativeLayout relativeLayout) {
        //TODO
        Random randomGenerator = new Random();
        Card card = playerHand.get(randomGenerator.nextInt(playerHand.size()));
        card.setResourceId(getResources().getIdentifier(card.toString(), "drawable", getPackageName()));
        renderCard(card, x_position, y_position, relativeLayout);
        roundCards.add(card);
        playerHand.remove(card);
    }

    // Create a card image and add it to the playing area
    private void renderCard(Card card, int x_position, int y_position, RelativeLayout relativeLayout) {
        ImageView imageView = new ImageView(this);

        imageView.setImageResource(card.getResourceId());
        imageView.setMaxHeight(165);
        imageView.setMaxWidth(165);
        imageView.setAdjustViewBounds(true);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = y_position;
        lp.leftMargin = x_position;
        imageView.setLayoutParams(lp);
        RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.playing_area);
        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.addView(imageView);
    }

    // Hide the card's image
    private void removeCardView(Card card) {
        ImageView imageView = (ImageView) findViewById(card.getId());

        if (imageView != null) {
            imageView.setVisibility(View.GONE);
        }
    }

    // Create the card images in player1's hand area
    private void drawInitialHand() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hand_area);

        for (Card card : player1) {
            int resID = getResources().getIdentifier(card.toString(), "drawable", getPackageName());
            card.setResourceId(resID);
            createNewImageView(resID, linearLayout, card.getId());
        }
    }

    // Clears the center/playing area and the cards in it
    private void collectRoundCards(View playingAreaView) {
        ((RelativeLayout) playingAreaView).removeAllViews();
        roundCards.clear();
    }

    private void setupBidTable() {
        TableLayout bidTable = (TableLayout) findViewById(R.id.bidTable);
        bidTable.setVisibility(View.VISIBLE);

        TableRow bidRow1 = (TableRow) findViewById(R.id.bidRow1);
        for (int i = 0; i < 4; i++) {
            Button bidButton = (Button) bidRow1.getChildAt(i);
            bidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBid != null) {
                        int resID = getResources().getIdentifier(selectedBid.getButtonId(), "id", getPackageName());
                        Button prevButton = (Button) findViewById(resID);
                        prevButton.setBackgroundColor(Color.RED);
                    }
                    Button viewButton = (Button) view;
                    viewButton.setBackgroundColor(Color.BLUE);
                    selectedBid = BidType.findBidType(viewButton.getText().toString());
                }
            });
        }

        TableRow bidRow2 = (TableRow) findViewById(R.id.bidRow2);
        for (int i = 0; i < 4; i++) {
            Button bidButton = (Button) bidRow2.getChildAt(i);
            bidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBid != null) {
                        int resID = getResources().getIdentifier(selectedBid.getButtonId(), "id", getPackageName());
                        Button prevButton = (Button) findViewById(resID);
                        prevButton.setBackgroundColor(Color.RED);
                    }
                    Button viewButton = (Button) view;
                    viewButton.setBackgroundColor(Color.BLUE);
                    selectedBid = BidType.findBidType(viewButton.getText().toString());
                }
            });
        }

        TableRow bidRow3 = (TableRow) findViewById(R.id.bidRow3);
        for (int i = 0; i < 4; i++) {
            Button bidButton = (Button) bidRow3.getChildAt(i);
            bidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBid != null) {
                        int resID = getResources().getIdentifier(selectedBid.getButtonId(), "id", getPackageName());
                        Button prevButton = (Button) findViewById(resID);
                        prevButton.setBackgroundColor(Color.RED);
                    }
                    Button viewButton = (Button) view;
                    viewButton.setBackgroundColor(Color.BLUE);
                    selectedBid = BidType.findBidType(viewButton.getText().toString());
                }
            });
        }

        TableRow bidRow4 = (TableRow) findViewById(R.id.bidRow4);
        for (int i = 0; i < 4; i++) {
            Button bidButton = (Button) bidRow4.getChildAt(i);
            bidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBid != null) {
                        int resID = getResources().getIdentifier(selectedBid.getButtonId(), "id", getPackageName());
                        Button prevButton = (Button) findViewById(resID);
                        prevButton.setBackgroundColor(Color.RED);
                    }
                    Button viewButton = (Button) view;
                    viewButton.setBackgroundColor(Color.BLUE);
                    selectedBid = BidType.findBidType(viewButton.getText().toString());
                }
            });
        }

        Button confirmButton = (Button) findViewById(R.id.submitBid);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableLayout bidTableOnConfirm = (TableLayout) findViewById(R.id.bidTable);
                bidTableOnConfirm.setVisibility(View.GONE);
            }
        });
    }
}

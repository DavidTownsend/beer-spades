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

        drawHand();

        setupBidTable();

        // TODO setup listener on submit button
    }

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
                RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.playing_area);
                // No cards in the center
                if (linearLayout.getChildCount() == 0 || linearLayout.getVisibility() == View.GONE) {
                    playCard(view);
                }
            }
        });

        linearLayout.addView(imageView);
    }

    private void playCard(View view) {
        Card card = getCard(view.getId());
        roundCards.add(card);
        removeCardView(card);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.playing_area);
        relativeLayout.setClickable(true);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectRoundCards(view);
            }
        });
        renderCard(card, 125, 200, relativeLayout);

        playAICards(player2, 0, 100, relativeLayout);
        playAICards(player3, 125, 0, relativeLayout);
        playAICards(player4, 250, 100, relativeLayout);

        removeCardView(card);

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

    private void playAICards(List<Card> playerHand, int x_position, int y_position, RelativeLayout relativeLayout) {
        //TODO
        Random randomGenerator = new Random();
        Card card = playerHand.get(randomGenerator.nextInt(playerHand.size()));
        card.setResourceId(getResources().getIdentifier(card.toString(), "drawable", getPackageName()));
        renderCard(card, x_position, y_position, relativeLayout);
        roundCards.add(card);
        playerHand.remove(card);
    }


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

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
            }
        });

        linearLayout.addView(imageView);
    }

    private Card getCard(int id) {
        for (Card card : allCards) {
            if (id == card.getId()) {
                return card;
            }
        }

        return null;
    }

    private void removeCardView(Card card) {
        ImageView imageView = (ImageView) findViewById(card.getId());
        imageView.setVisibility(View.GONE);
    }

    private void drawHand() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hand_area);

        for (Card card : player1) {
            int resID = getResources().getIdentifier(card.toString(), "drawable", getPackageName());
            card.setResourceId(resID);
            createNewImageView(resID, linearLayout, card.getId());
        }
    }

    private void collectRoundCards(View view) {
        for (Card card : roundCards) {
            removeCardView(card);
        }

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

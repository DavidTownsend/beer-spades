package com.lol.beer_spades.game;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.R;
import com.lol.beer_spades.player.Player;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by davidtownsend on 11/2/15.
 *
 * uhhh alex too
 */
public class GameActivity extends Activity {

    private static final String TAG = GameActivity.class.getSimpleName();

    ActionsByAI  aiAction;
    private List<Card> allCards;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<Card> roundCards;
    private BidType selectedBid;

    @Override
    // Initialization
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_game);

        allCards = CardUtilities.generateCards();

        player1 = new Player("Yoda");
        player2 = new Player("Luke");
        player3 = new Player("Anikan");
        player4 = new Player("Ja Ja");
        roundCards = new ArrayList<>();

        Collections.shuffle(allCards);
        for (int i = 0; i < 52; i++) {
            Card card1 = allCards.get(i++);
            card1.setPlayerName(player1.getPlayerName());
            player1.getCards().add(card1);

            Card card2 = allCards.get(i++);
            card2.setPlayerName(player2.getPlayerName());
            player2.getCards().add(card2);

            Card card3 = allCards.get(i++);
            card3.setPlayerName(player3.getPlayerName());
            player3.getCards().add(card3);

            Card card4 = allCards.get(i);
            card4.setPlayerName(player4.getPlayerName());
            player4.getCards().add(card4);
        }
        Collections.sort(player1.getCards());
        Collections.sort(player2.getCards());
        Collections.sort(player3.getCards());
        Collections.sort(player4.getCards());

        aiAction = new ActionsByAI();
        drawInitialHand();
        configurePlayingArea();
        setAIBids();

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
                    Card card = Card.pickWinner4(roundCards);
                    increaseWinnersTricks(card);

                    collectRoundCards(view);


                    //TODO cleanup
                    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.playing_area);
                    relativeLayout.setClickable(false);
                    if (StringUtils.equalsIgnoreCase(card.getPlayerName(), player1.getPlayerName())) {
                        //DO nothing
                    } else if (StringUtils.equalsIgnoreCase(card.getPlayerName(), player2.getPlayerName())) {
                        playAICards(player2.getCards(), 0, 100, relativeLayout);
                        playAICards(player3.getCards(), 125, 0, relativeLayout);
                        playAICards(player4.getCards(), 250, 100, relativeLayout);
                    } else if (StringUtils.equalsIgnoreCase(card.getPlayerName(), player3.getPlayerName())) {
                        playAICards(player3.getCards(), 125, 0, relativeLayout);
                        playAICards(player4.getCards(), 250, 100, relativeLayout);
                    } else {
                        playAICards(player4.getCards(), 250, 100, relativeLayout);
                    }
                }
            }
        });
    }

    private void increaseWinnersTricks(Card winningCard) {
        if (StringUtils.equalsIgnoreCase(winningCard.getPlayerName(), player1.getPlayerName())) {
            player1.increaseMade();
            updateP1BidsView();
        } else if (StringUtils.equalsIgnoreCase(winningCard.getPlayerName(), player2.getPlayerName())) {
            player2.increaseMade();
            updateP2BidsView();
        } else if (StringUtils.equalsIgnoreCase(winningCard.getPlayerName(), player3.getPlayerName())) {
            player3.increaseMade();
            updateP3BidsView();
        } else {
            player4.increaseMade();
            updateP4BidsView();
        }
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
                if(roundCards.size() != 4 & bidArea.getVisibility() == View.GONE){
                    selectCard(view, playingArea);
                }
//                if ((playingArea.getChildCount() == 0 || playingArea.getVisibility() == View.GONE)
//                        & bidArea.getVisibility() == View.GONE) {
//                    selectCard(view);
//                }
            }
        });

        linearLayout.addView(imageView);
    }

    // On-click for the card images. Increase or decrease the card's y value to show as selected
    private void selectCard(View view, RelativeLayout relativeLayout) {
        Card selectedCard = CardUtilities.getCard(allCards, view.getId());
        ImageView imageView = (ImageView) findViewById(view.getId());

        // If this card is already selected - deselect it
        if (selectedCard.isSelected()) {
            imageView.setY(imageView.getY() + 60);
            selectedCard.setSelected(false);
            relativeLayout.setClickable(false);
            return;
        }

        // Another card is already selected
        if (getSelectedCard() != null) {
            return;
        }

        selectedCard.setSelected(true);
        imageView.setY(imageView.getY() - 60);
        relativeLayout.setClickable(true);
    }

    // Get the card from player1 that is selected
    private Card getSelectedCard() {
        for (Card card : player1.getCards()) {
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
        relativeLayout.setClickable(true);

        renderCard(card, 125, 200, relativeLayout);

        if(roundCards.size() == 1) {
            playAICards(player2.getCards(), 0, 100, relativeLayout);
            playAICards(player3.getCards(), 125, 0, relativeLayout);
            playAICards(player4.getCards(), 250, 100, relativeLayout);
        }else if(roundCards.size() == 2){
            playAICards(player2.getCards(), 0, 100, relativeLayout);
            playAICards(player3.getCards(), 125, 0, relativeLayout);
        }else if(roundCards.size() == 3){
            playAICards(player2.getCards(), 0, 100, relativeLayout);
        }


        player1.getCards().remove(card);

        updateBidsView();
    }

    // Add a random AI card to the roundCards and playing area
    private void playAICards(List<Card> playerHand,int x_position, int y_position, RelativeLayout relativeLayout) {
        Random randomGenerator = new Random();
        Card card = aiAction.calculateNextCard(playerHand, roundCards);
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

        for (Card card : player1.getCards()) {
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

        // Dbl Nil - 1
        setupOnClickForBidButtons((TableRow) findViewById(R.id.bidRow1));

        // 2 - 5
        setupOnClickForBidButtons((TableRow) findViewById(R.id.bidRow2));

        // 6 - 9
        setupOnClickForBidButtons((TableRow) findViewById(R.id.bidRow3));

        // 10 - 13
        setupOnClickForBidButtons((TableRow) findViewById(R.id.bidRow4));

        Button confirmButton = (Button) findViewById(R.id.submitBid);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableLayout bidTableOnConfirm = (TableLayout) findViewById(R.id.bidTable);
                bidTableOnConfirm.setVisibility(View.GONE);
                player1.setBid(selectedBid.getValue());
                updateBidsView();
            }
        });
    }

    private void setupOnClickForBidButtons(TableRow bidRow) {
        for (int i = 0; i < 4; i++) {
            Button bidButton = (Button) bidRow.getChildAt(i);
            bidButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedBid != null) {
                        int resID = getResources().getIdentifier(selectedBid.getButtonId(), "id", getPackageName());
                        Button prevButton = (Button) findViewById(resID);
                        prevButton.setBackground(getResources().getDrawable(R.drawable.button));
                    }
                    Button viewButton = (Button) view;
                    viewButton.setBackground(getResources().getDrawable(R.drawable.button_selected));
                    selectedBid = BidType.findBidType(viewButton.getText().toString());
                }
            });
        }
    }

    private void setAIBids(){
        BiddingEngine.setBid(player2);
        BiddingEngine.setBid(player3);
        BiddingEngine.setBid(player4);
    }

    private void updateBidsView(){
        updateP1BidsView();
        updateP2BidsView();
        updateP3BidsView();
        updateP4BidsView();
    }

    private void updateP1BidsView() {
        TextView p1_tricks = (TextView) findViewById(R.id.p1_tricks);
        p1_tricks.setText(player1.getPlayerName() + "\n" + player1.getMade() + "/" + player1.getBid().toString());
    }

    private void updateP2BidsView() {
        TextView p2_tricks = (TextView) findViewById(R.id.p2_tricks);
        p2_tricks.setText(player2.getPlayerName() + "\n" + player2.getMade() + "/" + player2.getBid().toString());
    }

    private void updateP3BidsView() {
        TextView p3_tricks = (TextView) findViewById(R.id.p3_tricks);
        p3_tricks.setText(player3.getPlayerName() + "\n" + player3.getMade() + "/" + player3.getBid().toString());
    }

    private void updateP4BidsView() {
        TextView p4_tricks = (TextView) findViewById(R.id.p4_tricks);
        p4_tricks.setText(player4.getPlayerName() + "\n" + player4.getMade() + "/" + player4.getBid().toString());
    }
}

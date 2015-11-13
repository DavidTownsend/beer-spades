package com.lol.beer_spades.game;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.lol.beer_spades.MainMenuActivity;
import com.lol.beer_spades.R;
import com.lol.beer_spades.ai.ActionsByAI;
import com.lol.beer_spades.ai.BiddingEngine;
import com.lol.beer_spades.model.BidType;
import com.lol.beer_spades.model.Card;
import com.lol.beer_spades.model.Player;
import com.lol.beer_spades.model.SuitType;
import com.lol.beer_spades.render.BaseRenderActivity;
import com.lol.beer_spades.rules.Rules;
import com.lol.beer_spades.scoreboard.ScoreboardActivity;
import com.lol.beer_spades.utils.CardUtilities;
import com.lol.beer_spades.utils.FileUtilities;
import com.lol.beer_spades.utils.LogginUtils;
import com.lol.beer_spades.utils.ScreenUtilities;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtownsend on 11/2/15.
 *
 * uhhh alex too
 */
public class GameActivity extends BaseRenderActivity {

    //TODO is this needed
    private static final String TAG = GameActivity.class.getSimpleName();

    private ActionsByAI aiAction;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private List<Card> roundCards;
    private BidType selectedBid;
    private boolean spadesBeenPlayed;

    @Override
    // Initialization
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_game);

            Bundle players = this.getIntent().getExtras();
            if (players != null) {
                player1 = (Player) players.getSerializable("p1");
                player2 = (Player) players.getSerializable("p2");
                player3 = (Player) players.getSerializable("p3");
                player4 = (Player) players.getSerializable("p4");
            }

            if (player1 == null) {
                player1 = new Player("Yoda");
                player2 = new Player("Luke");
                player3 = new Player("Anikan");
                player4 = new Player("Ja Ja");
            }

            roundCards = new ArrayList<>();
            aiAction = new ActionsByAI();
            display = getWindowManager().getDefaultDisplay();
            spadesBeenPlayed = false;

            CardUtilities.dealCards(player1, player2, player3, player4);

            configureHandArea();
            drawInitialHand();
            configurePlayingArea();
            setAIBids();
            setupBidTable();

        }catch(Throwable e){
            Log.e(TAG, e.getMessage());
            LogginUtils.logHeap();
            LogginUtils.appendLog(e.getMessage());
        }
    }

    // Configure the center/playing area
    private void configurePlayingArea() {
        RelativeLayout playingArea = (RelativeLayout) findViewById(R.id.playing_area);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ScreenUtilities.getPlayAreaHeight(display), ScreenUtilities.getPlayAreaWidth(display));
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);
        playingArea.setLayoutParams(lp);

        playingArea.setClickable(true);
        playingArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPlayingArea(view);
            }
        });
    }

    private void onClickPlayingArea(View view){
        Card selectedCard = CardUtilities.getSelectCard(player1.getCards());

        // If a card was selected - play it
        if (selectedCard != null) {
            playCard(selectedCard);
            // If there are cards in the center
        } else if (roundCards != null && roundCards.size() != 0) {
            Card card = Card.pickWinner4(roundCards);
            increaseWinnersTricks(card);
            collectRoundCards(view);

            //if winning card is spade, set spadesbeenplayed to true
            if(SuitType.spades.equals(card.getSuitType())){
                spadesBeenPlayed = true;
            }

            // If hand over show scoreboard
            if (player1.isHandOver()) {
                Intent i = new Intent(getBaseContext(), ScoreboardActivity.class);
                Bundle players = new Bundle();
                players.putSerializable("p1", player1);
                players.putSerializable("p2", player2);
                players.putSerializable("p3", player3);
                players.putSerializable("p4", player4);
                i.putExtras(players);
                startActivity(i);
            } else {
                //TODO cleanup
                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.playing_area);
                relativeLayout.setClickable(false);
                Enum<SuitType> firstCardPlayedSuit = null;
                if (StringUtils.equalsIgnoreCase(card.getPlayerName(), player2.getPlayerName())) {
                    firstCardPlayedSuit = playAICard(player2.getCards(), ScreenUtilities.getPlayer2XCoordinate(relativeLayout), ScreenUtilities.getPlayer2YCoordinate(relativeLayout), relativeLayout).getSuitType();
                    playAICard(player3.getCards(), ScreenUtilities.getPlayer3XCoordinate(relativeLayout), ScreenUtilities.getPlayer3YCoordinate(relativeLayout), relativeLayout);
                    playAICard(player4.getCards(), ScreenUtilities.getPlayer4XCoordinate(relativeLayout), ScreenUtilities.getPlayer4YCoordinate(relativeLayout), relativeLayout);
                } else if (StringUtils.equalsIgnoreCase(card.getPlayerName(), player3.getPlayerName())) {
                    firstCardPlayedSuit = playAICard(player3.getCards(), ScreenUtilities.getPlayer3XCoordinate(relativeLayout), ScreenUtilities.getPlayer3YCoordinate(relativeLayout), relativeLayout).getSuitType();
                    playAICard(player4.getCards(), ScreenUtilities.getPlayer4XCoordinate(relativeLayout), ScreenUtilities.getPlayer4YCoordinate(relativeLayout), relativeLayout);
                } else if (StringUtils.equalsIgnoreCase(card.getPlayerName(), player4.getPlayerName())) {
                    firstCardPlayedSuit = playAICard(player4.getCards(), ScreenUtilities.getPlayer4XCoordinate(relativeLayout), ScreenUtilities.getPlayer4YCoordinate(relativeLayout), relativeLayout).getSuitType();
                }

                //Disable cards that cant be played
                Rules.cardsAllowedToPlay(player1.getCards(), firstCardPlayedSuit, spadesBeenPlayed);
                setupCardsInHand(player1.getCards());
            }
        }
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

    // Add the player1 card to the roundCards and playing area
    private void playCard(Card card) {
        roundCards.add(card);
        removeCardFromView(card);
        player1.getCards().remove(card);

        RelativeLayout playArea = (RelativeLayout) findViewById(R.id.playing_area);
        playArea.setClickable(true);

        renderCard(card, ScreenUtilities.getPlayer1XCoordinate(playArea), ScreenUtilities.getPlayer1YCoordinate(playArea), playArea);

        if(roundCards.size() == 1) {
            playAICard(player2.getCards(), ScreenUtilities.getPlayer2XCoordinate(playArea), ScreenUtilities.getPlayer2YCoordinate(playArea), playArea);
            playAICard(player3.getCards(), ScreenUtilities.getPlayer3XCoordinate(playArea), ScreenUtilities.getPlayer3YCoordinate(playArea), playArea);
            playAICard(player4.getCards(), ScreenUtilities.getPlayer4XCoordinate(playArea), ScreenUtilities.getPlayer4YCoordinate(playArea), playArea);
        }else if(roundCards.size() == 2){
            playAICard(player2.getCards(), ScreenUtilities.getPlayer2XCoordinate(playArea), ScreenUtilities.getPlayer2YCoordinate(playArea), playArea);
            playAICard(player3.getCards(), ScreenUtilities.getPlayer3XCoordinate(playArea), ScreenUtilities.getPlayer3YCoordinate(playArea), playArea);
        }else if(roundCards.size() == 3){
            playAICard(player2.getCards(), ScreenUtilities.getPlayer2XCoordinate(playArea), ScreenUtilities.getPlayer2YCoordinate(playArea), playArea);
        }

        updateBidsView();
    }

    // Add a random AI card to the roundCards and playing area
    private Card playAICard(List<Card> playerHand, int x_position, int y_position, RelativeLayout relativeLayout) {
        Card card = aiAction.calculateNextCard(playerHand, roundCards);
        card.setResourceId(getResourceId(card.toString(), DRAWABLE));
        renderCard(card, x_position, y_position, relativeLayout);

        roundCards.add(card);
        playerHand.remove(card);

        return card;
    }

    // Create the card images in player1's hand area
    private void drawInitialHand() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hand_area);
        Rules.cardsAllowedToPlay(player1.getCards(), null, spadesBeenPlayed);

        for (Card card : player1.getCards()) {
            ImageView imageView = new ImageView(this);
            card.setResourceId(getResourceId(card.toString(), DRAWABLE));
            setupImageViewFromCard(card, imageView, null, null);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout playingArea = (RelativeLayout) findViewById(R.id.playing_area);
                    TableLayout bidArea = (TableLayout) findViewById(R.id.bidTable);
                    // No cards in the center and bid selection isn't viewable
                    if (roundCards.size() != 4 & bidArea.getVisibility() == View.GONE) {
                        selectHumanPlayerCard(view, playingArea, player1.getCards());
                    }
                }
            });

            disableCardInHand(imageView, card);
            linearLayout.addView(imageView);
            linearLayout.invalidate();
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
                if (selectedBid != null) {
                    TableLayout bidTableOnConfirm = (TableLayout) findViewById(R.id.bidTable);
                    bidTableOnConfirm.setVisibility(View.GONE);
                    player1.setBid(selectedBid.getValue());
                    updateBidsView();
                }
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
                        Button prevButton = (Button) findViewById(getResourceId(selectedBid.getButtonId(), ID));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        FileUtilities.sendDataToFile(players);
        startActivity(new Intent(GameActivity.this, MainMenuActivity.class));
        finish();
    }
}

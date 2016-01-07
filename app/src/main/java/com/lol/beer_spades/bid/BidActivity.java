package com.lol.beer_spades.bid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.lol.beer_spades.R;
import com.lol.beer_spades.ai.ActionsByAI;
import com.lol.beer_spades.ai.BiddingEngine;
import com.lol.beer_spades.game.GameActivity;
import com.lol.beer_spades.model.BidType;
import com.lol.beer_spades.model.Card;
import com.lol.beer_spades.model.Player;
import com.lol.beer_spades.render.BaseRenderActivity;
import com.lol.beer_spades.utils.CardUtilities;


/**
 * Created by davidtownsend on 1/7/16.
 */
public class BidActivity extends BaseRenderActivity {

    private ActionsByAI aiAction;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private BidType selectedBid;

    @Override
    // Initialization
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bid);

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

        aiAction = new ActionsByAI();
        display = getWindowManager().getDefaultDisplay();

        CardUtilities.dealCards(player1, player2, player3, player4);

        configureHandArea();
        drawInitialHand();
        setAIBids();
        setupBidTable();
    }

    // Create the card images in player1's hand area
    private void drawInitialHand() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.hand_area);

        for (Card card : player1.getCards()) {
            ImageView imageView = new ImageView(this);
            card.setResourceId(getResourceId(card.toString(), DRAWABLE));
            setupImageViewFromCard(card, imageView, null, null);

            disableCardInHand(imageView, card);
            linearLayout.addView(imageView);
            //TODO what does invalidate do??
            linearLayout.invalidate();
        }
    }

    private void setAIBids(){
        BiddingEngine.setBid(player2);
        BiddingEngine.setBid(player3);
        BiddingEngine.setBid(player4);
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
                    //TableLayout bidTableOnConfirm = (TableLayout) findViewById(R.id.bidTable);
                    //bidTableOnConfirm.setVisibility(View.GONE);
                    player1.setBid(selectedBid.getValue());
                    removeAllCards(view);
                    Intent i = new Intent(getBaseContext(), GameActivity.class);
                    Bundle players = new Bundle();
                    players.putSerializable("p1", player1);
                    players.putSerializable("p2", player2);
                    players.putSerializable("p3", player3);
                    players.putSerializable("p4", player4);
                    i.putExtras(players);
                    startActivity(i);
                }
            }
        });
    }

    private void removeAllCards(View view){
        ((RelativeLayout) view.getParent().getParent().getParent()).removeAllViews();
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
}

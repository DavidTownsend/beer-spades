package com.lol.beer_spades.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidtownsend on 11/6/15.
 */
public class CardUtilities {

    public static List<Card> generateCards() {
        List<Card> deck = new ArrayList<>();
        List<SuitType> suits = retrieveSuit();
        Integer id = 0;

        for(int j = 0; j < 4; j++) {
            for (int i = 2; i <= 14; i++) {
                deck.add(new Card(id, suits.get(j), i));
                id ++;
            }
        }

        return deck;
    }

    private static List<SuitType> retrieveSuit(){
        List<SuitType> suit = new ArrayList<>();

        suit.add(SuitType.clubs);
        suit.add(SuitType.hearts);
        suit.add(SuitType.spades);
        suit.add(SuitType.diamonds);

        return suit;
    }

    // TODO not being used
    public static void setupBasicImageProperties(ImageView imageView, Card card, Integer x_position, Integer y_position){
        imageView.setImageResource(card.getResourceId());
        imageView.setMaxHeight(165);
        imageView.setMaxWidth(165);
        imageView.setAdjustViewBounds(true);
        imageView.setId(card.getId());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(null != x_position && null != y_position) {
            lp.topMargin = y_position;
            lp.leftMargin = x_position;
        }

        imageView.setLayoutParams(lp);
    }

    public static Card getCard(List<Card> allCards, int id){
        for(Card card : allCards){
            if(id == card.getId()){
                return card;
            }
        }

        return null;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

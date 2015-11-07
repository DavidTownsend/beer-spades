package com.lol.beer_spades.game;

import java.util.List;

/**
 * Created by Steven on 11/6/2015.
 */
public class ActionsByAI
{

  protected Card calculateNextCard(List<Card> AIHand, List<Card> playedCards, List<Card> cardsOnTable)
  {
      //First card in array is expected to be the first card played.
      Card firstCardPlayed = cardsOnTable.get(0);

      if(haveSameSuit(firstCardPlayed, AIHand))
      {
          Card highestCard = haveHigherCard(firstCardPlayed,AIHand,cardsOnTable);
          if(highestCard != null)
          {
              return highestCard;
          }

          return lowestCardOfThatSuit(firstCardPlayed.getSuitType(),AIHand);
      }
    return lowestCardInHand(AIHand);
  }

  private Card lowestCardOfThatSuit(Enum suit,List<Card> AIHand)
    {
        for (int i=0; i < AIHand.size();i++)
        {
            if(AIHand.get(i).getSuitType().equals(suit))
            {
                return AIHand.get(i);
            }
        }
        return null;
    }
  private Card lowestCardInHand(List<Card> AIHand)
  {
      Card lowestHeart = null;
      Card lowestDiamond = null;
      Card lowestClub = null;

      //Find the lowest card for each suit
      for (int i=0; i < AIHand.size();i++)
      {
        //Just ignore spades for now for first phase
        if(!AIHand.get(i).getSuitType().equals(SuitType.spades))
        {
            if(lowestHeart == null && AIHand.get(i).getSuitType().equals(SuitType.hearts))
            {
                lowestHeart = AIHand.get(i);
            }
            if(lowestClub == null && AIHand.get(i).getSuitType().equals(SuitType.clubs))
            {
                lowestClub =  AIHand.get(i);
            }
            if(lowestDiamond == null && AIHand.get(i).getSuitType().equals(SuitType.diamonds))
            {
                lowestDiamond =  AIHand.get(i);
            }
        }
      }

      if(lowestHeart == null)
      {
          //lowestHeart = new Card(1, 2, 15);
      }
      if (lowestHeart.getCardNumber() <= lowestDiamond.getCardNumber())
      {
          if(lowestHeart.getCardNumber() <= lowestClub.getCardNumber())
          {
              return lowestHeart;
          }
      }
      else if(lowestDiamond.getCardNumber() <= lowestClub.getCardNumber())
      {
          return lowestDiamond;
      }

      return lowestClub;
  }
//TODO: this method will be implemented later to calculate the chance of winning the hand.
/*    private  boolean canIWin(Card firstCardPlayed,List<Card> AIHand,List<Card> cardsOnTable)
    {
        for (Card card:cardsOnTable)
        {
          //TODO: add logic for caring about spades
          if(card.getSuiteType().equals(firstCardPlayed.getSuiteType()))
          {
              if(!haveHigherCard(card,AIHand))
              {
                  return false;
              }
          }
        }
        return true;
    }*/

  private Card haveHigherCard(Card firstCardPlayed,List<Card> AIHand, List<Card> cardsOnTable)
  {
      for (Card card:cardsOnTable)
      {
          //TODO: add logic for caring about spades
          //Check to see if suit type matches the first card played, if it doesnt then the card value doesnt matter.
          if(card.getSuitType().equals(firstCardPlayed.getSuitType()))
          {
            for (Card cardFromAiHand:AIHand)
            {
              if(cardFromAiHand.getSuitType().equals(card.getSuitType()) && cardFromAiHand.getCardNumber() > card.getCardNumber())
              {
                return cardFromAiHand;
              }
            }
          }
      }
      return null;
  }

  private boolean haveSameSuit(Card card, List<Card> AIHand)
  {
      for (Card cardFromHand:AIHand)
      {
          if (cardFromHand.getSuitType().equals(card.getSuitType()))
          {
              return true;
          }
      }
    return false;
  }
}

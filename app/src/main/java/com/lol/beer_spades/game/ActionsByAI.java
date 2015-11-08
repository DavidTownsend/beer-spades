package com.lol.beer_spades.game;

import java.util.List;

/**
 * Created by Steven on 11/6/2015.
 */
public class ActionsByAI
{
    public Card highestCardOnTable = null;
    Card firstCardPlayed = null;

  protected Card calculateNextCard(List<Card> AIHand,  List<Card> cardsOnTable)
  {
      //First card in array is expected to be the first card played.
      //highestCardOnTable = cardsOnTable.get(0);

      if(cardsOnTable == null || cardsOnTable.isEmpty())
      {
          highestCardOnTable = null;
          return playHighestCardInHand(AIHand);
      }
      else{
          //This is to set highest card if player starts the round.
          if(highestCardOnTable == null)
          {
              highestCardOnTable =cardsOnTable.get(0);
          }
          firstCardPlayed = cardsOnTable.get(0);
      }

      if(haveSameSuit(AIHand))
      {
          if(haveHigherCard(AIHand,cardsOnTable))
          {
              return highestCardOnTable;
          }

          return lowestCardOfThatSuit(firstCardPlayed.getSuitType(),AIHand);
      }
    return lowestCardInHand(AIHand);
  }

    private Card playHighestCardInHand(List<Card> AIHand)
    {


        //Find the lowest card
        for (int i=0; i < AIHand.size();i++)
        {
            //Just ignore spades for now for first phase
            if(!AIHand.get(i).getSuitType().equals(SuitType.spades))
            {
                if(highestCardOnTable == null)
                {
                    highestCardOnTable = AIHand.get(i);
                }
                else
                {
                    if(AIHand.get(i).getCardNumber() > highestCardOnTable.getCardNumber())
                    {
                        highestCardOnTable = AIHand.get(i);
                    }
                }

            }
        }

        //If still null this means the player only has Spades left so pick a spade.
        if(highestCardOnTable == null)
        {
            highestCardOnTable = AIHand.get(0);
        }
        return highestCardOnTable;
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
      Card lowestCard = null;

      //Find the lowest card
      for (int i=0; i < AIHand.size();i++)
      {
        //Just ignore spades for now for first phase
        if(!AIHand.get(i).getSuitType().equals(SuitType.spades))
        {
          if(lowestCard == null)
          {
              lowestCard = AIHand.get(i);
          }
          else
          {
              if(AIHand.get(i).getCardNumber() <=lowestCard.getCardNumber())
              {
                  lowestCard = AIHand.get(i);
              }
          }

        }
      }

      //If still null this means the player only has Spades left so pick a spade.
     if(lowestCard == null)
     {
         lowestCard = AIHand.get(0);
     }
      return lowestCard;
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

  private boolean haveHigherCard(List<Card> AIHand, List<Card> cardsOnTable)
  {

          //TODO: add logic for caring about spades
          //Check to see if suit type matches the first card played, if it doesnt then there is no need to compare your hand to this card.
            for (Card cardFromAiHand:AIHand)
            {
              if(cardFromAiHand.getSuitType().equals(firstCardPlayed.getSuitType()) && cardFromAiHand.getCardNumber() > highestCardOnTable.getCardNumber())
              {
                  highestCardOnTable = cardFromAiHand;
                  return true;
              }
            }

      return false;
  }

  private boolean haveSameSuit(List<Card> AIHand)
  {
      for (Card cardFromHand:AIHand)
      {
          if (cardFromHand.getSuitType().equals(firstCardPlayed.getSuitType()))
          {
              return true;
          }
      }
    return false;
  }
}

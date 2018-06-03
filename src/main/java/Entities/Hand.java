package Entities;

import Enums.CardRank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Bananka
 */
public class Hand {
    private List<Card> cards;
    private int score;
    private boolean softHand;
    private int bet;

    public Hand() {
        cards = new ArrayList<>();
        score = 0;
        softHand = false;
        bet = 0;
    }

    /**
     * Adds given card to hand and accordingly adjusts score and whether tha hand is 'soft'.
     * @param card  card to be added to hand
     */
    public void takeCard(Card card) {
        if (!softHand && card.getRank().equals(CardRank.Ace)) {
            softHand = true;
        }
        this.cards.add(card);
        this.score += card.getValue();
        if (score > 21 && softHand) {
            //ace will change it's value from 11 to 1
            score -= 10;
            softHand = false;
        }
    }

    /**
     * Determines whether tha hand can be split.
     * @return  true if hand can be split, false otherwise
     */
    public boolean canSplit() {
        if (cards.size() != 2) {
            return false;
        }
        return cards.get(0).getValue() == cards.get(1).getValue();
    }

    /**
     * Splits the hand and adjusts needed fields.
     * @return  card taken form hand by split and to be added to second hand
     */
    public Card doSplit() {
        Card card = cards.remove(1);
        score = cards.get(0).getValue();
        if (cards.get(0).getRank().equals(CardRank.Ace)) {
            softHand = true;
        }
        return card;
    }

    public void resetHand() {
        cards.clear();
        score = 0;
        softHand = false;
        bet = 0;
    }

    /**
     * Determines whether there is Blackjack on the hand.
     * @return  true in case of Blackjack
     */
    public boolean hasBlackjack() {
        return score == 21 && cards.size() == 2;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}

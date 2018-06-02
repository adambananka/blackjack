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

    public boolean canSplit() {
        if (cards.size() != 2) {
            return false;
        }
        return cards.get(0).getValue() == cards.get(1).getValue();
    }

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

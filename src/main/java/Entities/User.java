package Entities;

import Enums.CardRank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Bananka
 */
public abstract class User {
    private String name;
    private List<Card> cards;
    private int score;
    private boolean softHand;

    protected User(String name) {
        this.name = name;
        cards = new ArrayList<>();
        score = 0;
        softHand = false;
    }

    public void takeCard(Card card) {
        if (!softHand && card.getRank().equals(CardRank.Ace)) {
            softHand = true;
        }
        this.cards.add(card);
        this.score += card.getValue();
        if (score > 21 && softHand) {
            score -= 10;
            softHand = false;
        }
    }

    public void resetHand() {
        cards.clear();
        score = 0;
        softHand = false;
    }

    public boolean hasBlackjack() {
        return score == 21 && cards.size() == 2;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }
}

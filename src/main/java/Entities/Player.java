package Entities;

import Enums.CardRank;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Bananka
 */
public class Player {
    private String name;
    private List<Card> cards = new ArrayList<>();
    private int score = 0;
    private boolean softHand = false;

    public Player(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        return score;
    }

    public boolean hasSoftHand() {
        return softHand;
    }
}

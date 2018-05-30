package Entities;

import Enums.CardRank;
import Enums.CardSuit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Adam Bananka
 */
public class Deck {
    private static final int NUMBER_OF_DECKS = 1; //one deck consists of 52 cards

    private List<Card> cards = new ArrayList<>();
    private List<Card> removed = new ArrayList<>();

    public Deck() {
        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                for (int i = 0; i < NUMBER_OF_DECKS; i++) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
        shuffleDeck();
    }

    public void resetDeck() {
        cards.addAll(removed);
        removed.clear();
        shuffleDeck();
    }

    private void shuffleDeck() {
        Collections.shuffle(cards);
        /*Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--)
        {
            int index = random.nextInt(i + 1);
            Card tmp = cards.remove(index);
            cards.add(index, cards.remove(i));
            cards.add(i, tmp);
        }*/
    }

    public Card getNextCard() {
        Card card = cards.remove(0);
        removed.add(card);
        return card;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Card> getRemoved() {
        return removed;
    }
}

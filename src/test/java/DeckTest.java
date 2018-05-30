import Entities.Card;
import Entities.Deck;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Adam Bananka
 */
public class DeckTest {
    private Deck deck;

    @Before
    public void setup() {
        deck = new Deck();
    }

    @Test
    public void getNextCard() {
        assertThat(deck.getRemoved())
                .isEmpty();
        int size = deck.getCards().size();
        Card card = deck.getNextCard();
        assertThat(deck.getCards().size())
                .isEqualTo(size - 1);
        assertThat(deck.getRemoved().get(0))
                .isEqualTo(card);
    }

    @Test
    public void resetDeck() {
        deck.getNextCard();
        Card card = deck.getRemoved().get(0);
        int size = deck.getCards().size();
        deck.resetDeck();
        assertThat(deck.getRemoved())
                .isEmpty();
        assertThat(deck.getCards().size())
                .isEqualTo(size + 1);
        assertThat(deck.getCards())
                .contains(card);
    }
}

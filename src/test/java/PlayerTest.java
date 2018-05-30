import Entities.Card;
import Entities.Player;
import Enums.CardRank;
import Enums.CardSuit;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Adam Bananka
 */
public class PlayerTest {
    private Player player;
    private Card ace;
    private Card king;
    private Card seven;

    @Before
    public void setup() {
        player = new Player("Adam");
        ace = new Card(CardSuit.Hearts, CardRank.Ace);
        king = new Card(CardSuit.Clubs, CardRank.King);
        seven = new Card(CardSuit.Diamonds, CardRank.Seven);
    }

    @Test
    public void takeCard() {
        assertThat(player.getCards())
                .isEmpty();
        assertThat(player.getScore())
                .isEqualTo(0);
        player.takeCard(king);
        assertThat(player.getCards())
                .containsOnly(king);
        assertThat(player.getScore())
                .isEqualTo(10);
    }

    @Test
    public void takeCardAceOnHandValueChange() {
        player.takeCard(ace);
        player.takeCard(seven);
        assertThat(player.getScore())
                .isEqualTo(18);
        player.takeCard(king);
        assertThat(player.getScore())
                .isEqualTo(18);
    }

    @Test
    public void takeCardAceTakenValueChange() {
        player.takeCard(seven);
        player.takeCard(king);
        assertThat(player.getScore())
                .isEqualTo(17);
        player.takeCard(ace);
        assertThat(player.getScore())
                .isEqualTo(18);
    }
}

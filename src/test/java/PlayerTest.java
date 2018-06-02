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
        assertThat(player.getDefaultHand().getCards())
                .isEmpty();
        assertThat(player.getDefaultHand().getScore())
                .isEqualTo(0);
        player.getDefaultHand().takeCard(king);
        assertThat(player.getDefaultHand().getCards())
                .containsOnly(king);
        assertThat(player.getDefaultHand().getScore())
                .isEqualTo(10);
    }

    @Test
    public void takeCardAceOnHandValueChange() {
        player.getDefaultHand().takeCard(ace);
        player.getDefaultHand().takeCard(seven);
        assertThat(player.getDefaultHand().getScore())
                .isEqualTo(18);
        player.getDefaultHand().takeCard(king);
        assertThat(player.getDefaultHand().getScore())
                .isEqualTo(18);
    }

    @Test
    public void takeCardAceTakenValueChange() {
        player.getDefaultHand().takeCard(seven);
        player.getDefaultHand().takeCard(king);
        assertThat(player.getDefaultHand().getScore())
                .isEqualTo(17);
        player.getDefaultHand().takeCard(ace);
        assertThat(player.getDefaultHand().getScore())
                .isEqualTo(18);
    }
}

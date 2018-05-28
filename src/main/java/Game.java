import Entities.Deck;
import Entities.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Bananka
 */
public class Game {
    private Player dealer;
    private List<Player> players;
    private Deck deck;

    public Game() {
        dealer = new Player("Dealer");
        deck = new Deck();
        players = new ArrayList<>();
    }

    
}

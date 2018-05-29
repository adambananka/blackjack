import Entities.Deck;
import Entities.Player;
import Enums.Result;

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

    //TODO check blackjack, soft hand, bets, surrender, DB results, split

    public void run() {
        ConsoleUI.initialMessage();
        initializePlayers();
        do {
            resetGameData();
            ConsoleUI.gameBeginMessage();
            makeInitialDeal();
            for (Player player : players) {
                makeTurn(player);
            }
            makeDealerTurn();
            finalizeHand();
        } while (ConsoleUI.wantNextHand());
    }

    private void initializePlayers() {
        int numOfPlayers = ConsoleUI.getNumberOfPlayers();
        for (int i = 0; i < numOfPlayers; i++) {
            String name = ConsoleUI.getPlayerName(i + 1);
            players.add(new Player(name));
        }
    }

    private void makeInitialDeal() {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.takeCard(deck.getNextCard());
            }
            dealer.takeCard(deck.getNextCard());
        }
        ConsoleUI.reportDealerCard(dealer.getCards().get(0));
        for (Player player : players) {
            ConsoleUI.reportPlayerCards(player.getName(), player.getCards(), player.getScore());
        }
    }

    private void makeTurn(Player player) {
        ConsoleUI.reportPlayerTurn(player.getName(), player.getCards(), player.getScore());
        do {
            String move;
            if (player.getCards().size() == 2) {
                move = ConsoleUI.getMove(true);
            } else {
                move = ConsoleUI.getMove(false);
            }
            switch (move) {
                case "hit":
                    player.takeCard(deck.getNextCard());
                    break;
                case "stand":
                    return;
                case "surrender":
                    return;
            }
            ConsoleUI.reportPlayerCards(player.getCards(), player.getScore());
        } while (player.getScore() < 21);
    }

    private void makeDealerTurn() {
        ConsoleUI.reportDealerTurn(dealer.getCards(), dealer.getScore());
        while (dealer.getScore() < 17) {
            dealer.takeCard(deck.getNextCard());
            ConsoleUI.reportDealerHit(dealer.getCards(), dealer.getScore());
        }
    }

    private void finalizeHand() {
        int dealerScore = dealer.getScore();
        Result dealerResult = Result.None;
        if (dealerScore > 21) {
            dealerResult = Result.Bust;
        }
        ConsoleUI.reportResult(dealer.getName(), dealerScore, dealerResult);
        for (Player player : players) {
            int playerScore = player.getScore();
            if (playerScore > 21) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Bust);
            } else if (dealerScore > 21 || playerScore > dealerScore) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Win);
            } else if (playerScore < dealerScore) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Lose);
            } else if (playerScore == dealerScore) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Push);
            }
        }
    }

    private void resetGameData() {
        deck.resetDeck();
        dealer.resetHand();
        for (Player player : players) {
            player.resetHand();
        }
    }
}

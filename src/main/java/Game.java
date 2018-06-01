import Entities.Dealer;
import Entities.Deck;
import Entities.Player;
import Enums.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Bananka
 */
public class Game {
    private ResultManager resultManager;

    private Dealer dealer;
    private List<Player> players;
    private Deck deck;

    public Game() {
        resultManager = new ResultManager();
        dealer = new Dealer();
        deck = new Deck();
        players = new ArrayList<>();
    }

    //TODO split

    public void run() {
        ConsoleUI.initialMessage();
        initializePlayers();
        do {
            resetGameData();
            makeBets();
            ConsoleUI.gameBeginMessage();
            makeInitialDeal();
            for (Player player : players) {
                makeTurn(player);
            }
            makeDealerTurn();
            finalizeHand();
            removePlayersOutOfChips();
        } while (ConsoleUI.wantNextHand());
        makeFinalSummary();
        resultManager.saveAllResultLists();
        System.exit(0);
    }

    private void initializePlayers() {
        int numOfPlayers = ConsoleUI.getNumberOfPlayers();
        for (int i = 0; i < numOfPlayers; i++) {
            String name = ConsoleUI.getPlayerName(i + 1);
            players.add(new Player(name));
        }
    }

    private void makeBets() {
        for (Player player : players) {
            int bet = ConsoleUI.getPlayerBet(player.getName(), player.getChips());
            player.setBet(bet);
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
        if (player.hasBlackjack()) {
            return;
        }
        ConsoleUI.reportPlayerTurn(player.getName(), player.getCards(), player.getScore());
        do {
            String move;
            //player has possibility to surrender only as first move (that means he/she only has 2 cards)
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
                    player.evaluateBetSurrender();
                    player.resetHand();
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
        Result dealerResult = null;
        if (dealerScore > 21) {
            dealerResult = Result.Busted;
        } else if (dealer.hasBlackjack()) {
            dealerResult = Result.Blackjack;
        }
        ConsoleUI.reportResult(dealer.getName(), dealerScore, dealerResult);
        for (Player player : players) {
            int playerScore = player.getScore();
            if (playerScore > 21) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Busted);
                player.evaluateBetLost();
                resultManager.addResultList(player.getName(), player.getCards().toString(), playerScore, player.getBet(), Result.Busted);
            } else if (playerScore < dealerScore && dealerScore <= 21 && playerScore > 0) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Lost);
                player.evaluateBetLost();
                resultManager.addResultList(player.getName(), player.getCards().toString(), playerScore, player.getBet(), Result.Lost);
            } else if (playerScore == dealerScore) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Push);
                //in case of Push, player do not win or lose anything
                resultManager.addResultList(player.getName(), player.getCards().toString(), playerScore, player.getBet(), Result.Push);
            } else if (player.hasBlackjack()) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Blackjack);
                player.evaluateBetBlackjack();
                resultManager.addResultList(player.getName(), player.getCards().toString(), playerScore, player.getBet(), Result.Blackjack);
            } else if (dealerScore > 21 || playerScore > dealerScore) {
                ConsoleUI.reportResult(player.getName(), playerScore, Result.Won);
                player.evaluateBetWon();
                resultManager.addResultList(player.getName(), player.getCards().toString(), playerScore, player.getBet(), Result.Won);
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

    private void removePlayersOutOfChips() {
        List<Player> toRemove = new ArrayList<>();
        for (Player player : players) {
            if (player.getChips() == 0) {
                toRemove.add(player);
                ConsoleUI.reportPlayerOutOfChips(player.getName());
            }
        }
        players.removeAll(toRemove);
        if (players.isEmpty()) {
            ConsoleUI.reportNoMorePlayers();
            System.exit(0);
        }
    }

    private void makeFinalSummary() {
        for (Player player : players) {
            ConsoleUI.reportFinalChips(player.getName(), player.getChips());
        }
    }
}

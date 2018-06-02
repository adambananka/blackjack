import Entities.Dealer;
import Entities.Deck;
import Entities.Hand;
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
            finalizeRound();
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
            player.getDefaultHand().setBet(bet);
        }
    }

    private void makeInitialDeal() {
        //at the start player always has only one hand
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                player.getDefaultHand().takeCard(deck.getNextCard());
            }
            dealer.getDefaultHand().takeCard(deck.getNextCard());
        }
        ConsoleUI.reportDealerCard(dealer.getDefaultHand().getCards().get(0));
        for (Player player : players) {
            ConsoleUI.reportPlayerCards(player.getName(), player.getDefaultHand().getCards(), player.getDefaultHand().getScore());
        }
    }

    private void makeTurn(Player player) {
        if (player.getDefaultHand().hasBlackjack()) {
            return;
        }
        for (int i = 0; i < player.getHands().size(); i++) {
            Hand hand = player.getHands().get(i);
            ConsoleUI.reportPlayerTurn(player.getName(), hand.getCards(), hand.getScore());
            do {
                String move;
                //player has possibility to surrender only as first move (that means he/she only has 2 cards)
                boolean canSurrender = hand.getCards().size() == 2;
                boolean canSplit = hand.canSplit() && player.getChips() >= hand.getBet();
                move = ConsoleUI.getMove(canSurrender, canSplit);
                switch (move) {
                    case "hit":
                        hand.takeCard(deck.getNextCard());
                        break;
                    case "stand":
                        return;
                    case "surrender":
                        player.evaluateBetSurrender(hand.getBet());
                        hand.resetHand();
                        return;
                    case "split":
                        splitHand(player, hand);
                        break;
                }
                ConsoleUI.reportPlayerCards(hand.getCards(), hand.getScore());
            } while (hand.getScore() < 21);
        }
    }

    private void splitHand(Player player, Hand hand) {
        Hand newHand = player.splitHand(hand);
        hand.takeCard(deck.getNextCard());
        newHand.takeCard(deck.getNextCard());
    }

    private void makeDealerTurn() {
        ConsoleUI.reportDealerTurn(dealer.getDefaultHand().getCards(), dealer.getDefaultHand().getScore());
        while (dealer.getDefaultHand().getScore() < 17) {
            dealer.getDefaultHand().takeCard(deck.getNextCard());
            ConsoleUI.reportDealerHit(dealer.getDefaultHand().getCards(), dealer.getDefaultHand().getScore());
        }
    }

    private void finalizeRound() {
        int dealerScore = dealer.getDefaultHand().getScore();
        Result dealerResult = null;
        if (dealerScore > 21) {
            dealerResult = Result.Busted;
        } else if (dealer.getDefaultHand().hasBlackjack()) {
            dealerResult = Result.Blackjack;
        }
        ConsoleUI.reportResult(dealer.getName(), dealerScore, dealerResult);
        for (Player player : players) {
            for (Hand hand : player.getHands()) {
                int playerScore = hand.getScore();
                if (playerScore > 21) {
                    ConsoleUI.reportResult(player.getName(), playerScore, Result.Busted);
                    player.evaluateBetLost(hand.getBet());
                    resultManager.addResultList(player.getName(), hand.getCards().toString(), playerScore, hand.getBet(), Result.Busted);
                } else if (playerScore < dealerScore && dealerScore <= 21 && playerScore > 0) {
                    ConsoleUI.reportResult(player.getName(), playerScore, Result.Lost);
                    player.evaluateBetLost(hand.getBet());
                    resultManager.addResultList(player.getName(), hand.getCards().toString(), playerScore, hand.getBet(), Result.Lost);
                } else if (playerScore == dealerScore) {
                    ConsoleUI.reportResult(player.getName(), playerScore, Result.Push);
                    //in case of Push, player do not win or lose anything
                    resultManager.addResultList(player.getName(), hand.getCards().toString(), playerScore, hand.getBet(), Result.Push);
                } else if (hand.hasBlackjack()) {
                    ConsoleUI.reportResult(player.getName(), playerScore, Result.Blackjack);
                    player.evaluateBetBlackjack(hand.getBet());
                    resultManager.addResultList(player.getName(), hand.getCards().toString(), playerScore, hand.getBet(), Result.Blackjack);
                } else if (dealerScore > 21 || playerScore > dealerScore) {
                    ConsoleUI.reportResult(player.getName(), playerScore, Result.Won);
                    player.evaluateBetWon(hand.getBet());
                    resultManager.addResultList(player.getName(), hand.getCards().toString(), playerScore, hand.getBet(), Result.Won);
                }
            }
        }
    }

    private void resetGameData() {
        deck.resetDeck();
        dealer.resetHands();
        for (Player player : players) {
            player.resetHands();
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
            resultManager.saveAllResultLists();
            System.exit(0);
        }
    }

    private void makeFinalSummary() {
        for (Player player : players) {
            ConsoleUI.reportFinalChips(player.getName(), player.getChips());
        }
    }
}

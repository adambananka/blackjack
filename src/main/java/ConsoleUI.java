import Entities.Card;
import Enums.Result;

import java.util.List;
import java.util.Scanner;

/**
 * @author Adam Bananka
 */
public class ConsoleUI {

    private static Scanner scanner = new Scanner(System.in);

    public static void initialMessage() {
        System.out.println("Welcome to Blackjack game!");
    }

    public static int getNumberOfPlayers() {
        System.out.println("How many players will play? (1-6):");
        String input = scanner.nextLine();
        if (input.length() > 1) {
            System.out.println("Invalid input.");
            return getNumberOfPlayers();
        }
        try {
            int num = Integer.parseInt(input);
            if (num < 1 || num > 6) {
                System.out.println("Invalid input.");
                return getNumberOfPlayers();
            }
            return num;
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input.");
            return getNumberOfPlayers();
        }
    }

    public static String getPlayerName(int playerNumber) {
        System.out.println("Enter name of player " + playerNumber + ":");
        return scanner.nextLine();
    }

    public static int getPlayerBet(String player, int chips) {
        System.out.println(player + " you have " + chips + " chips. How much you want to bet?");
        String input = scanner.nextLine();
        try {
            int bet = Integer.parseInt(input);
            if (bet <= chips && bet > 0) {
                return bet;
            }
            System.out.println("Invalid input.");
            return getPlayerBet(player, chips);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid input.");
            return getPlayerBet(player, chips);
        }
    }

    public static void gameBeginMessage() {
        System.out.println("Let's begin. Each player is dealt two cards.");
    }

    public static void reportDealerCard(Card card) {
        System.out.println("Dealer got " + card + " and one hidden card.");
    }

    public static void reportPlayerCards(String player, List<Card> cards, int score) {
        StringBuilder message = new StringBuilder(player + " was dealt ");
        message.append(cardsToString(cards))
                .append(". His score is ")
                .append(score)
                .append(".");
        if (score == 21 && cards.size() == 2) {
            message.append(" You have Blackjack!");
        }
        System.out.println(message);
    }

    public static void reportPlayerCards(List<Card> cards, int score) {
        StringBuilder message = new StringBuilder("You have ");
        message.append(cardsToString(cards))
                .append(". Your score is ")
                .append(score)
                .append(".");
        System.out.println(message);
    }

    public static void reportPlayerTurn(String player, List<Card> cards, int score) {
        System.out.println(player + ", it's your turn!");
        reportPlayerCards(cards, score);
    }

    public static String getMove(boolean first) {
        reportOptions(first);
        String input = scanner.nextLine().toLowerCase();
        if (input.equals("hit") || input.equals("stand")) {
            return input;
        }
        if (first && input.equals("surrender")) {
            return input;
        }
        System.out.println("Invalid input.");
        return getMove(false);
    }

    public static void reportDealerTurn(List<Card> cards, int score) {
        System.out.println("Dealer's turn!");
        reportDealerCards(cards, score);
    }

    public static void reportDealerHit(List<Card> cards, int score) {
        System.out.println("Dealer hits!");
        reportDealerCards(cards, score);
    }

    public static void reportResult(String player, int score, Result result) {
        StringBuilder message = new StringBuilder(player);
        message.append(" has scored: ")
                .append(score)
                .append(".");
        if (result != null) {
            message.append(" You have ")
                    .append(result)
                    .append("!");
        }
        System.out.println(message);
    }

    public static boolean wantNextHand() {
        System.out.println("Do you want next hand? ('yes' to confirm)");
        String input = scanner.nextLine().toLowerCase();
        return input.equals("yes");
    }

    private static void reportOptions(boolean first) {
        StringBuilder message = new StringBuilder("You have these options: Hit, Stand");
        if (first) {
            message.append(", Surrender");
        }
        message.append(". What's your move?");
        System.out.println(message);
    }

    private static void reportDealerCards(List<Card> cards, int score) {
        StringBuilder message = new StringBuilder("He has ");
        message.append(cardsToString(cards))
                .append(". His score is ")
                .append(score)
                .append(".");
        System.out.println(message);
    }

    private static String cardsToString(List<Card> cards) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            message.append(cards.get(i).toString());
            if (i < cards.size() - 1) {
                message.append(", ");
            }
        }
        return message.toString();
    }
}

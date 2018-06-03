package Entities;

/**
 * @author Adam Bananka
 */
public class Player extends User {
    private static final int INITIAL_CHIPS = 100;

    private int chips;

    public Player(String name) {
        super(name);
        chips = INITIAL_CHIPS;
    }

    /**
     * Splits given hand to two, by moving one card to new hand and placing bet equal the one on given hand.
     * @param hand  hand to split
     * @return  new hand created by split
     */
    public Hand splitHand(Hand hand) {
        Hand newHand = new Hand();
        getHands().add(newHand);
        newHand.takeCard(hand.doSplit());
        newHand.setBet(hand.getBet());
        return newHand;
    }

    /**
     * Adjusts chips based on given bet value and result - Blackjack.
     * @param bet   value of given bet
     */
    public void evaluateBetBlackjack(int bet) {
        chips += Math.round(1.5 * bet);
    }

    /**
     * Adjusts chips based on given bet value and result - win.
     * @param bet   value of given bet
     */
    public void evaluateBetWon(int bet) {
        chips += bet;
    }

    /**
     * Adjusts chips based on given bet value and result - surrender.
     * @param bet   value of given bet
     */
    public void evaluateBetSurrender(int bet) {
        chips -= Math.round(0.5 * bet);
    }

    /**
     * Adjusts chips based on given bet value and result - lose.
     * @param bet   value of given bet
     */
    public void evaluateBetLost(int bet) {
        chips -= bet;
    }

    public int getChips() {
        return chips;
    }
}

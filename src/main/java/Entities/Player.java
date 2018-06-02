package Entities;

/**
 * @author Adam Bananka
 */
public class Player extends User {
    private static final int INITIAL_CHIPS = 100;

    private int chips;
//    private int bet;

    public Player(String name) {
        super(name);
        chips = INITIAL_CHIPS;
    }

//    @Override
//    public void resetHand() {
//        super.resetHand();
//        bet = 0;
//    }

    public Hand splitHand(Hand hand) {
        Hand newHand = new Hand();
        getHands().add(newHand);
        newHand.takeCard(hand.doSplit());
        newHand.setBet(hand.getBet());
        return newHand;
    }

    public void evaluateBetBlackjack(int bet) {
        chips += Math.round(1.5 * bet);
    }

    public void evaluateBetWon(int bet) {
        chips += bet;
    }

    public void evaluateBetSurrender(int bet) {
        chips -= Math.round(0.5 * bet);
    }

    public void evaluateBetLost(int bet) {
        chips -= bet;
    }

    public int getChips() {
        return chips;
    }

//    public int getBet() {
//        return bet;
//    }

//    public void setBet(int bet) {
//        this.bet = bet;
//    }
}

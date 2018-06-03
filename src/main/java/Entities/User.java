package Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Bananka
 */
public abstract class User {
    private String name;
    private List<Hand> hands;

    protected User(String name) {
        this.name = name;
        hands = new ArrayList<>();
        hands.add(new Hand());
    }

    public void resetHands() {
        if (hands.size() > 1) {
            hands.clear();
            hands.add(new Hand());
            return;
        }
        hands.get(0).resetHand();
    }

    public String getName() {
        return name;
    }

    public List<Hand> getHands() {
        return hands;
    }

    public Hand getDefaultHand() {
        return hands.get(0);
    }
}

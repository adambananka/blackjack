package Enums;

/**
 * @author Adam Bananka
 */
public enum CardRank {
    Two(2),
    Three(3),
    Four(4),
    Five(5),
    Six(6),
    Seven(7),
    Eight(8),
    Nine(9),
    Ten(10),
    Jack(110),
    Queen(210),
    King(310),
    Ace(11);

    private int value;

    CardRank(int value) {
        this.value = value;
    }

    public int getValue() {
        if (value > 11) {
            return value % 100;
        }
        return value;
    }
}

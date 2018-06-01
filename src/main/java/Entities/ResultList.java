package Entities;

import Enums.Result;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Adam Bananka
 */
@Entity
public class ResultList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 1, max = 32)
    private String player;
    @NotNull
    @Size(min = 1)
    private String cards;
    @Min(0)
    private int score;
    @Min(0)
    private int bet;
    @Enumerated
    private Result result;

    public ResultList() {
    }

    public ResultList(String player, String cards, int score, int bet, Result result) {
        this.player = player;
        this.cards = cards;
        this.score = score;
        this.bet = bet;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getCards() {
        return cards;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultList{" +
                "id=" + id +
                ", player='" + player + '\'' +
                ", cards='" + cards + '\'' +
                ", score=" + score +
                ", bet=" + bet +
                ", result=" + result +
                '}';
    }
}

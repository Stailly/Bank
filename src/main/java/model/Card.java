package model;

import java.util.Objects;

public class Card {
    private int account;
    private long number;
    private int owner;

    public Card(int account) {
        this.account = account;
    }

    public Card(int account, int owner) {
        this.account = account;
        this.owner = owner;
    }

    public Card(long number, int account, int owner) {
        this.account = account;
        this.number = number;
        this.owner = owner;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public long getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return account == card.account && number == card.number && owner == card.owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, number, owner);
    }
}

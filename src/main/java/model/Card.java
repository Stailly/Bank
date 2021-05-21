package model;

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
}

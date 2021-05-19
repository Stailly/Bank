package model;

import java.math.BigInteger;

public class Card {
    private BigInteger account;
    private int client;
    private long cardNumber;

    public Card(BigInteger account, int client) {
        this.account = account;
        this.client = client;
    }
}

package model;

import java.math.BigInteger;
import java.util.Objects;

public class Account {
    private int id;
    private int owner;
    private BigInteger number;
    private double balance;

    public Account() {
        this.balance = 0;
    }

    public Account(int owner) {
        this.owner = owner;
        this.balance=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && owner == account.owner && Double.compare(account.balance, balance) == 0 && Objects.equals(number, account.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, number, balance);
    }
}

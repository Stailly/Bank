package controller;

import exceptions.FormatException;
import model.Card;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static db.DBMethods.counter;

public class CardContTest {
    CardCont controller = new CardCont();

    @Test(expected = NumberFormatException.class)
    public void createCardTest1() throws FormatException {
        controller.createCard(new BigInteger("1234567890123456789s"));
    }

    @Test(expected = FormatException.class)
    public void createCardTest2() throws FormatException {
        controller.createCard(new BigInteger("1234567890123456789"));
    }

    @Test
    public void saveCardTest() {
        int countBefore = counter("cards");
        Card card = new Card(1234567812345670L, 10, 212);
        controller.save(card);
        int count = counter("cards");
        Assert.assertEquals(1, count - countBefore);
    }

    @Test
    public void getCardTest() {
        Card exp = new Card(1234567812345670L, 10, 212);
        Card card = controller.get(212);
        Assert.assertEquals(exp, card);
    }
}

package service;

import exceptions.FormatException;
import org.junit.Test;

import java.math.BigInteger;

public class MethodsTest {
    Methods methods=new Methods();

    @Test(expected = NumberFormatException.class)
    public void createCardTest1() throws FormatException {
        methods.createCard(new BigInteger("1234567890123456789s"));
    }

    @Test(expected = FormatException.class)
    public void createCardTest2() throws FormatException {
        methods.createCard(new BigInteger("1234567890123456789"));
    }
    @Test(expected = FormatException.class)
    public void addCardTest1() throws FormatException {
        methods.addCard(1234567890123456789L, 5,8);
    }
}

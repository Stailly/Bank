import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.sun.net.httpserver.HttpServer;
import controller.AccountCont;
import controller.CardCont;
import model.controllermodels.AccountMod;
import model.controllermodels.CardMod;
import exceptions.FormatException;
import model.Card;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.util.List;

public class App {
    private static ObjectMapper mapper = new ObjectMapper();
    private static CardCont cardDao = new CardCont();
    private static AccountCont accountDao = new AccountCont();
    private final static int SERVER_PORT = 8070;

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);

        server.createContext("/getAllCardsOfClient", exchange -> {
            int clientID = Integer.parseInt(exchange.getRequestURI().toString().split("\\?")[1].split("=")[1]);
            List<Card> list = cardDao.getCards(clientID);

            ArrayNode array = mapper.valueToTree(list);
            JsonNode result = mapper.createObjectNode().set("card", array);

            String cardResult = result.toString();

            exchange.sendResponseHeaders(200, cardResult.length());
            OutputStream outputStream = exchange.getResponseBody();

            outputStream.write(cardResult.getBytes());
            outputStream.close();
        });

        server.createContext("/addNewCardByAccount", exchange -> {
            CardMod cardInfo = mapper.readValue(exchange.getRequestBody(), CardMod.class);
            try {
                cardDao.createCard(cardInfo.getAccount());
            } catch (FormatException e) {
                e.printStackTrace();
            }

        });

        server.createContext("/addMoney", exchange -> {
            AccountMod am = mapper.readValue(exchange.getRequestBody(), AccountMod.class);
            accountDao.addMoneyToAcc(new BigInteger(am.getNumber()), am.getMoneyToAdd());
        });

        server.createContext("/getBalanceInfo", exchange -> {
            String accountNum = exchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
            Double balance = accountDao.checkAccountBalance(new BigInteger(accountNum));

            String result = mapper.writeValueAsString(balance);

            exchange.sendResponseHeaders(200, result.length());
            OutputStream outputStream = exchange.getResponseBody();

            outputStream.write(result.getBytes());
            outputStream.close();
        });

        server.start();


    }
}

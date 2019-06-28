package idwall.desafio;

import idwall.desafio.crawler.BoTika;
import idwall.desafio.crawler.RedditThreadService;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Created by lucas on 25/06/19.
 */
public class CrawlersMain {

    public static void main(String[] args) throws IOException {
//        rodaParte1();
        rodaParte2();
    }

    private static void rodaParte1() throws IOException {
        RedditThreadService redditThreadService = new RedditThreadService();
        redditThreadService.listaTopThreads("askreddit;worldnews;cats");
    }

    private static void rodaParte2() {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new BoTika());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

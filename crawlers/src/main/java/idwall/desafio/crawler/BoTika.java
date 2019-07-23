package idwall.desafio.crawler;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

/**
 * Created by lucas on 27/06/19.
 */
public class BoTika extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String mensagem = update.getMessage().getText();
            if (mensagem.startsWith("/NadaPraFazer")) {
                enviaTopThreads(update, mensagem);
            }
        }
    }

    public String getBotUsername() {
        return "";
    }

    public String getBotToken() {
        return "";
    }

    private void enviaTopThreads(Update update, String mensagem) {
        String[] mensagemSplit = mensagem.split(" ");
        if (mensagemSplit.length == 2) {
            RedditThreadService redditThreadService = new RedditThreadService();
            String resposta = "";

            String subredditList = mensagemSplit[1];
            String[] subreddits = subredditList.split(";");

            for (String subreddit : subreddits) {
                try {
                    List<RedditThreadInfo> redditThreadInfoList = redditThreadService.buscaTopThreads(subreddit);
                    resposta = resposta + redditThreadService.topTreadsToString(redditThreadInfoList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            SendMessage message = new SendMessage().setChatId(update.getMessage().getChatId()).setText(resposta);
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}

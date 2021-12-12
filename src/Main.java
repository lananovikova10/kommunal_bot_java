import Bot.Bot$_04;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        // register the bot
        try {
            telegramBotsApi.registerBot(new Bot$_04());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
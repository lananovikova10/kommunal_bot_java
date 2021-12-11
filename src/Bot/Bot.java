package Bot;

import Calcs.Calcs;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {

    private static Logger log = Logger.getLogger(Bot.class.getName());

    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    private Calcs calcs;


    @Override
    public void onUpdateReceived(Update update) {

        update.getUpdateId();
        long chat_id = update.getMessage().getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Long.toString(chat_id));

        String text = update.getMessage().getText();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        log.info("Message from user: " + text);

        try {
            String textToSend = getMessageFromKeyboard(text);
            sendMsg(String.valueOf(chat_id), textToSend);
        } catch (TelegramApiException | InstantiationException | IllegalAccessException | IOException e) {
            log.log(Level.SEVERE, "Exception: ", e.toString());
        }

    }


    @Override
    public String getBotUsername() {
        return "@komunal_bot_java_bot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "BOT_TOKEN";
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    public synchronized void sendMsg(String chatId, String msg) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(msg);
        execute(sendMessage);
    }

    public String getMessageFromKeyboard(String msg) throws InstantiationException, IllegalAccessException,
            IOException {

        Calcs calcs = new Calcs();
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (msg.equals("/start") || msg.equals("Привет") || msg.equals("привет")) {
            keyboard.clear();
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardFirstRow.add("Последние");
            keyboardFirstRow.add("Новые");
            keyboardSecondRow.add("Все показания");
            keyboardSecondRow.add("История");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Выбирай!";
        }

        if (msg.equals("Последние")) {
            log.info("Last data requested");
            msg = calcs.getLastLine();
            return "Последние показания (т1, т2, горячая, холодная): " + msg;
        }

        if (msg.equals("Все показания")) {
            log.info("All data requested");
            String allData = calcs.allData();
            return allData;
        }

        if (msg.startsWith("/new")) {
            log.info("Activating getting the new data");

            msg = calcs.calcNew(msg);
            return "Насчиталось (сумма, свет, вода): " + msg;
        }

        if (msg.startsWith("/save")) {
            log.info("User has requested to save new data");

            msg = calcs.saveNewToFile(msg);
            return "Saved";
        }

        return msg;
    }


}
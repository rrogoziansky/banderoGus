package org.example;

import com.sun.javafx.collections.MappingChange;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;

import static javax.swing.UIManager.put;

public class Main extends TelegramLongPollingBot {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new Main());
    }

    @Override
    public String getBotUsername() {
        return "BanderogusRR_bot";
    }

    @Override
    public String getBotToken() {
        return "6246219334:AAGCjxNMUASHPAPk_JobnZ_ZmcJRXLUMXJQ";
    }

    @Override
    public void onUpdateReceived(Update update) {

        Long chatId = getChatId(update);

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            SendMessage message = createMessage("Привіт!");
            message.setChatId(chatId);
            attachButtons(message, new HashMap<String, String>() {{ put("Слава Україні", "glory_for_ukraine"); }});
            sendApiMethodAsync(message);
        }

        if (update.hasCallbackQuery()){
            if (update.getCallbackQuery().getData().equals("glory_for_ukraine")){
                SendMessage message = createMessage("Героям Слава");
                message.setChatId(chatId);

                attachButtons(message, new HashMap<String, String>() {{ put("Слава Нації!", "glory_of_the_nation"); }});
                sendApiMethodAsync(message);
            }
        }

        if (update.hasCallbackQuery()){
            if (update.getCallbackQuery().getData().equals("glory_of_the_nation")){
                SendMessage message = createMessage("Смерть Ворогам");
                message.setChatId(chatId);
                sendApiMethodAsync(message);
            }
        }
    }

    public Long getChatId( Update update){

        if (update.hasMessage()){
           return update.getMessage().getFrom().getId();
        }
        if (update.hasCallbackQuery()){
            return update.getCallbackQuery().getFrom().getId();
        }
        return null;
    }

    public SendMessage createMessage(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8) );
        message.setParseMode("markdown");
        return message;
    }

    public void attachButtons(SendMessage message, Map<String, String> buttons) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String buttonName : buttons.keySet()) {
            String buttonValue = buttons.get(buttonName);

            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(new String(buttonName.getBytes(), StandardCharsets.UTF_8) );
            button.setCallbackData(buttonValue);

            keyboard.add(Arrays.asList(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }
}
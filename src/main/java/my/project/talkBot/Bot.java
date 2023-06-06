package my.project.talkBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {
    final private String BOT_TOKEN = "6230689111:AAF_yShMA9xNMwy4veGFeydgu1KlxpD94jM";
    final private String BOT_NAME = "talk99bot";
    public Storage storage = new Storage();


    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                // Извлекаем из объекта сообщение пользователя
                Message inMess = update.getMessage();
                //Достаем из inMess id чата пользователя
                String chatId = inMess.getChatId().toString();
                //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
                String response = parseMessage(inMess.getText());
                //Создаем объект класса SendMessage - наш будущий ответ пользователю
                SendMessage outMess = new SendMessage();

                //Добавляем в наше сообщение id чата а также наш ответ
                outMess.setChatId(chatId);
                outMess.setText(response);

                //Отправка в чат
                execute(outMess);
            }
        } catch (TelegramApiException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String parseMessage(String text) throws IOException, InterruptedException {
        String response = "";

        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (text.equals("/start"))
            response = "Привет! Это бот-цитата. Жми /get, чтобы получить цитату на сегодня";
        else if (text.equals("/get"))
            response = storage.getQuote();
        else
            response = "Сообщение не распознано";

        return response;
    }
}

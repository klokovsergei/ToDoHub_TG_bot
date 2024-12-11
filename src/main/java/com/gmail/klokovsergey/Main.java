package com.gmail.klokovsergey;


import com.gmail.klokovsergey.common.ToDoHubBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {

    private static String TELEGRAM_BOT_NAME;
    private static String TELEGRAM_BOT_TOKEN;
    private static final String FILE_BOT = "src/main/resources/notForInternet/loginBot";


    public static void main(String[] args) throws TelegramApiException {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_BOT));
            TELEGRAM_BOT_NAME = reader.readLine();
            TELEGRAM_BOT_TOKEN = reader.readLine();
            reader.close();
        } catch (Exception e) {
            System.out.println("Файл с данными бота не найден.");
            System.exit(90);
        }

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new ToDoHubBot(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN));

    }
}
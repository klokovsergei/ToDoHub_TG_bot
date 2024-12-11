package com.gmail.klokovsergey.common.entity.view;

import com.gmail.klokovsergey.common.MultisessionTelegramBot;
import com.gmail.klokovsergey.common.entity.StatusMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public class Viewer {

    public static void sendAnswer(Update update) {

    }

    public static String loadMessage(String nameFile) {
        try {
            var is = ClassLoader.getSystemResourceAsStream("messages/" + nameFile);
            System.out.println("я во вьюере");
            return new String(is.readAllBytes());
        } catch (IOException e) { throw new RuntimeException("Can't load message: messages/" + nameFile); }
    }
}

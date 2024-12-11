package com.gmail.klokovsergey.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.klokovsergey.common.entity.StatusMode;
import com.gmail.klokovsergey.common.entity.database.CashUsersHub;
import com.gmail.klokovsergey.common.entity.view.Viewer;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ToDoHubBot extends MultisessionTelegramBot{
    /**
     * Обработка Эвентов в ТГ
     * @param update (методы для класса Update)
     */
    @Override
    public void onUpdateEventRecieved(Update update) {
        String userMessage = getMessageText();
        StatusMode currentStatusMode = cashUsersHub.getUserStatusMode(update.getMessage().getChatId());

        System.out.println(currentStatusMode);

        if (userMessage.equals("/start")) {
            String answer = Viewer.loadMessage("start2");
            System.out.println(answer);
            sendTextMessage(answer);
            cashUsersHub.setUserStatusMode(getCurrentChatId(), StatusMode.START);
            return;
        }

        if (userMessage.equals("/admin") && update.getMessage().getChatId() == adminID){
            cashUsersHub.setUserStatusMode(update.getMessage().getChatId(), StatusMode.ADMIN);
            String answer = Viewer.loadMessage("admin");
            sendTextMessage(answer);
            showMainMenu("Вернуться в режим START", "/start",
                    "ВЫКЛ  бота", "/switch_off");
            return;
        }

        if (!userMessage.equals("/start") && currentStatusMode == StatusMode.START) {
            sendTextMessage(validMenuSelection);
            return;
        }


        if (currentStatusMode == StatusMode.ADMIN) {
            switch (userMessage) {
                case "/start" -> cashUsersHub.setUserStatusMode(getCurrentChatId(), StatusMode.START);
                case "/exit" -> {

                    System.exit(0);
                }
            }

            return;
        }





    }

    //region Конструкторы

    /**
     * Конструктор телеграм бота
     * @param nameBot имя бота (без @)
     * @param token токет бота от BotFather
     */
    public ToDoHubBot(String nameBot, String token) {
        super(nameBot, token);
        cashUsersHub = new CashUsersHub();
    }

    //endregion

    //region Поля
    CashUsersHub cashUsersHub;
    private final long adminID = 214904629L;
    private final String validMenuSelection = "Выберите раздел меню или введите корректную команду.";
    //endregion

    private static final String FILE_JSON = "src/main/resources/notForInternet/log/users_cash.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
}

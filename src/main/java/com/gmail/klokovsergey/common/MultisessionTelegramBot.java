package com.gmail.klokovsergey.common;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.GetMyCommands;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeChat;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonCommands;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MultisessionTelegramBot extends TelegramLongPollingBot {

    //region Методы

    /**
     * Метод возвращает текст из последнего сообщения
     * @return текст или "";
     */
    public String getMessageText() {
        return updateEvent.get().hasMessage() ? updateEvent.get().getMessage().getText() : "нет сообщения";
    }
    /**
     * Метод возвращает текст из последнего поста в канале
     * @return текст или "";
     */
    public String getChannelPostText() {
        return updateEvent.get().hasChannelPost() ? updateEvent.get().getChannelPost().getText() : "нет поста";
    }
    /**
     * Метод отправляет в чат HTML (текстовое сообщение).
     * Поддерживается html-разметка.
     * @return Message class;
     */
    public Message sendHtmlMessage(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("html");
        message.setChatId(getCurrentChatId());
        return executeTelegramApiMethod(message);
    }


    public Message sendTextMessage(String text) {
        long underscoreCount = text != null ? text.chars().filter(c -> c == '_').count() : 0;
        if (underscoreCount % 2 == 0) {
            SendMessage command = createApiSendMessageCommand(String.valueOf(text));
            return executeTelegramApiMethod(command);
        } else {
            var message = "Строка '%s' является невалидной с точки зрения markdown. Воспользуйтесь методом sendHtmlMessage()".formatted(text);
            System.out.println(message);
            return sendHtmlMessage(message);
        }
    }


    //сформировать сообщение для чата в ТГ
    private SendMessage createApiSendMessageCommand(String text) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setParseMode("markdown");
        message.setChatId(getCurrentChatId());
        return message;
    }

    private <T extends Serializable, Method extends BotApiMethod<T>> T executeTelegramApiMethod(Method method) {
        try {
            return super.sendApiMethod(method);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Message executeTelegramApiMethod(SendPhoto message) {
        try {
            return super.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    //id текущего чата
    public Long getCurrentChatId() {
        if (updateEvent.get().hasMessage()) {
            return updateEvent.get().getMessage().getFrom().getId();
        }

        if (updateEvent.get().hasCallbackQuery()) {
            return updateEvent.get().getCallbackQuery().getFrom().getId();
        }

        return null;
    }

    public void showMainMenu(String... commands) {
        ArrayList<BotCommand> list = new ArrayList<>();

        //convert strings to command list
        for (int i = 0; i < commands.length; i += 2) {
            String description = commands[i];
            String key = commands[i+1];

            if (key.startsWith("/")) //remove first /
                key = key.substring(1);

            BotCommand bc = new BotCommand(key, description);
            list.add(bc);
        }

        //get commands list
        var chatId = getCurrentChatId();
        GetMyCommands gmcs = new GetMyCommands();
        gmcs.setScope(BotCommandScopeChat.builder().chatId(chatId).build());
        ArrayList<BotCommand> oldCommands = executeTelegramApiMethod(gmcs);

        //ignore commands change for same command list
        if (oldCommands.equals(list))
            return;

        //set commands list
        SetMyCommands cmds = new SetMyCommands();
        cmds.setCommands(list);
        cmds.setScope(BotCommandScopeChat.builder().chatId(chatId).build());
        executeTelegramApiMethod(cmds);

        //show menu button
        var ex = new SetChatMenuButton();
        ex.setChatId(chatId);
        ex.setMenuButton(MenuButtonCommands.builder().build());
        executeTelegramApiMethod(ex);
    }

    //endregion

    //region Override методы
    @Override
    public final void onUpdateReceived(Update updateEvent) {
        try {
            this.updateEvent.set(updateEvent);
            onUpdateEventRecieved(this.updateEvent.get());
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public void onUpdateEventRecieved(Update updateEvent) {
        //do nothing (child class - override)
    }

    @Override
    public String getBotUsername() { return nameBot; }
    @Override
    public String getBotToken() { return token; }

    //endregion

    //region Конструкторы

    public MultisessionTelegramBot(String nameBot, String token) {
        this.nameBot = nameBot;
        this.token = token;

        updateEvent = new ThreadLocal<>();
    }

    //endregion

    //region Поля
    private final String nameBot;
    private final String token;
    private ThreadLocal<Update> updateEvent;
    //endregion
}

package com.programm;

import com.programm.Bot.bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new bot());
            System.out.println("Bot is ready!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

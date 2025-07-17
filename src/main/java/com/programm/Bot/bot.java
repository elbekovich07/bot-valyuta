package com.programm.Bot;

import com.programm.Config.BotConfig;
import com.programm.Service.ValyutaBtnService;
import com.programm.Service.ValyutaService;
import com.programm.dbService.DBService;
import com.programm.paload.Currency;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.programm.Service.KeyboardService.mainKeyboard;


public class bot extends TelegramLongPollingBot {

    private final Map<String, Boolean> awaitingHelp = new HashMap<>();


    public bot() {
    }

    private void getKurs(Update update) {
        String selected = update.getMessage().getText();
        Map<String, Currency> getBase = ValyutaService.getInstance().getBase();
        Currency currency = getBase.get(selected);

        SendMessage sendMessage;
        if (currency != null) {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), currency.toString());
        } else {
            sendMessage = new SendMessage(update.getMessage().getChatId().toString(), "Kechirasiz hech narsa topilmadi.");
        }
        executor(sendMessage);

    }

    private void executor(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.BOT_TOKEN;
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String text = update.getMessage().getText();
        String chatId = update.getMessage().getChatId().toString();
        Long userId = update.getMessage().getFrom().getId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        if (ValyutaService.getInstance().keys().contains(text)) {
            getKurs(update);
            return;
        }


        if (awaitingHelp.getOrDefault(chatId, false)) {
            sendMessage.setText("Rahmat! Sizning murojaatingiz qabul qilindi:\n\n📨 " + text + "\n \n Tez orada ko'rib chiqamiz!");
            SendMessage msg = new SendMessage();
            msg.setChatId("5276890220");
            msg.setText("Sizga yangi murojat:\n " + text);
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            update.getMessage().getFrom().getId();
            DBService.saveMessage(userId, text);
            sendMessage.setReplyMarkup(mainKeyboard());
            awaitingHelp.remove(chatId);
        } else {
            switch (text) {
                case "/start" -> {
                    sendMessage.setText("Assalomu alaykum botga hush kelibsiz");
                    sendMessage.setReplyMarkup(mainKeyboard());
                    System.out.println(update.getMessage().getFrom());
                }

                case "Start", "Orqaga🔙" -> {
                    sendMessage.setText("Asosiy menu");
                    sendMessage.setReplyMarkup(mainKeyboard());
                }

                case "Infoℹ️" ->
                        sendMessage.setText("Bu bot sizga har kuni yangilanib boradigan valyutalarni kuzatib borish imkonini beradi.");

                case "Kontakt📞" ->
                        sendMessage.setText("Biz bilan aloqa📞: +998 XX XXX XX XX\nTelegram 📩 : @elbekovich_2");

                case "Yordam🆘" -> {
                    sendMessage.setText("Qanday yordam bera olaman? Murojatingizni shu yerga yozing:");
                    awaitingHelp.put(chatId, true);
                }

                case "Valyutalarni ko'rish💵" -> {
                    List<String> keys = new ArrayList<>(ValyutaService.getInstance().keys());

                    SendMessage msg = new SendMessage();
                    msg.setChatId(chatId);
                    msg.setText("Valyutani tanlang:");
                    msg.setReplyMarkup(ValyutaBtnService.getInstance().getValyutaBtnSerivece());



                    try {
                        executor(msg);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    return;
                }


                default -> sendMessage.setText("Noto‘g‘ri buyruq. Menyudan foydalaning.");
            }

            if (!"Yordam🆘".equals(text)) {
                sendMessage.setReplyMarkup(mainKeyboard());
            }
        }
        System.out.printf("Foydalanuvchi: %s (@%s)%n", update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getUserName());


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}

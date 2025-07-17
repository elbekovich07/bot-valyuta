package com.programm.Service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardService {

    public static ReplyKeyboardMarkup mainKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();

        row1.add("Infoℹ️");
        row1.add("Kontakt📞");
        row2.add("Yordam🆘");
        row2.add("Valyutalarni ko'rish💵");
        keyboardRows.add(row1);
        keyboardRows.add(row2);

        keyboard.setKeyboard(keyboardRows);

        keyboard.setOneTimeKeyboard(true);
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }

    public static ReplyKeyboardMarkup getValyutaBtnService(List<String> keys) {

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();


        for (String key : keys) {
            KeyboardRow keyRow = new KeyboardRow();
            keyRow.add(key);
            keyboardRows.add(keyRow);
        }


        KeyboardRow back = new KeyboardRow();
        back.add("Orqaga🔙");
        keyboardRows.add(back);


        keyboard.setKeyboard(keyboardRows);
        keyboard.setOneTimeKeyboard(false);
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }

    public static InlineKeyboardMarkup getInlineKeyboardMarkup(String currencyCode) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> inline = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        InlineKeyboardButton historyBtn = new InlineKeyboardButton();
        historyBtn.setText("So'ngi 30 kunni ko'rish");
        historyBtn.setCallbackData("History_" + currencyCode);

        row.add(historyBtn);
        inline.add(row);

        markup.setKeyboard(inline);
        return markup;
    }
}

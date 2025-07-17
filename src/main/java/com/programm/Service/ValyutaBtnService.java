package com.programm.Service;

import com.programm.paload.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;

public class ValyutaBtnService {
    private ValyutaBtnService() {
    }

    private static ValyutaBtnService instance;

    public static ValyutaBtnService getInstance() {
        if (instance == null) {
            instance = new ValyutaBtnService();
        }
        return instance;
    }

    public List<String> getValyutaBtnService() {
        List<String> priority = List.of("AQSH dollari", "EVRO", "Rossiya rubli", "Xitoy yuani");

        List<String> all = ValyutaService.getInstance().getBase().values().stream().map(Currency::getCcynm_uz).distinct().toList();

        List<String> sorted = all.stream().filter(name -> !priority.contains(name)).sorted().toList();

        List<String> result = new ArrayList<>(priority);
        result.addAll(sorted);

        return result;
    }

    public ReplyKeyboardMarkup getValyutaBtnSerivece() {
        return KeyboardService.getValyutaBtnService(getValyutaBtnService());
    }

}

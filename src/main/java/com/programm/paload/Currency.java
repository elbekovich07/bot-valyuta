package com.programm.paload;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Currency {
    private Integer id;
    @SerializedName(value = "Code")
    private String code;
    @SerializedName(value = "Ccy")
    private String ccy;
    @SerializedName(value = "CcyNm_RU")
    private String ccynm_ru;
    @SerializedName(value = "CcyNm_UZ")
    private String ccynm_uz;
    @SerializedName(value = "CcyNm_UZC")
    private String ccynm_uzc;
    @SerializedName(value = "CcyNm_EN")
    private String ccynm_en;
    @SerializedName(value = "Nominal")
    private String nominal;
    @SerializedName(value = "Rate")
    private String rate;
    @SerializedName(value = "Diff")
    private String diff;
    @SerializedName(value = "Date")
    private String date;

    @Override
    public String toString() {
        String arrow = diff.contains("-") ? "📉" : "📈";

        String formattedDate = date;
        try {
            Date parsedDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(date);
            formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);
        } catch (ParseException _) {
        }

        return String.format("""
                💱 Valyuta: %s (%s)
                💰 %s %s = %s so'm
                %s Kurs o'zgarishi: %s
                📅 Sana: %s""", ccynm_uzc, ccy, nominal, ccy, rate, arrow, diff, formattedDate);
    }
}
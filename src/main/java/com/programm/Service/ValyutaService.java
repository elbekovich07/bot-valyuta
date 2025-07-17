package com.programm.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programm.paload.Currency;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ValyutaService {
    public ValyutaService() {
    }

    private static ValyutaService instance;


    public static ValyutaService getInstance() {

        if ((instance) == null) {
            instance = new ValyutaService();
        }

        instance.updateBase();
        return instance;
    }

    private static Map<String, Currency> base = new HashMap<>();

    public Map<String, Currency> getBase() {
        return base;
    }

    public Set<String> keys() {
        return base.keySet();
    }

    public void updateBase() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://cbu.uz/uz/arkhiv-kursov-valyut/json/")).build();
        try {
            HttpResponse<String> send = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = send.body();
            GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat("DD.MM.yyyy").create().newBuilder();
            Gson gson = gsonBuilder.create();
            List<Currency> currencyList = new ArrayList<>(Arrays.asList(gson.fromJson(body, Currency[].class)));

            base = currencyList.stream().collect(Collectors.toMap(Currency::getCcynm_uz, Function.identity()));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        saveRatesToFile();
    }

    public void saveRatesToFile() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String filename = "valyuta_" + LocalDate.now() + ".json";
            Path dirPath = Paths.get("Currency_history");
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path filePath = dirPath.resolve(filename);
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                gson.toJson(base, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
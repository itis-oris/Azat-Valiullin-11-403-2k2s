package com.itis.musiclabel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyService {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public BigDecimal getUsdToRubRate() {
        try {
            String url = "https://www.cbr-xml-daily.ru/daily_json.js";
            Request request = new Request.Builder().url(url).build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    JsonNode root = mapper.readTree(json);

                    BigDecimal rate = BigDecimal.valueOf(
                            root.get("Valute").get("USD").get("Value").asDouble()
                    );
                    System.out.println("USD/RUB rate (CBR): " + rate);
                    return rate;
                }
            }
        } catch (Exception e) {
            System.err.println("CBR API error: " + e.getMessage());
        }
        return BigDecimal.valueOf(71);
    }

    public BigDecimal convertUsdToRub(BigDecimal usd) {
        return usd.multiply(getUsdToRubRate()).setScale(2, RoundingMode.HALF_UP);
    }
}
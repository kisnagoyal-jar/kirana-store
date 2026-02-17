package com.kirana.kirana_register.service.helper;

import com.kirana.kirana_register.dto.response.FxRatesResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.time.Duration;

@Service
public class CurrencyRateService {

    private static final String REDIS_KEY = "fx:rate:USD:INR";
    private static final Duration CACHE_TTL = Duration.ofMinutes(30);
    private static final String FX_API_URL = "https://api.fxratesapi.com/latest";

    private final RedisTemplate<String, Object> redisTemplate;
    private final RestTemplate restTemplate;

    public CurrencyRateService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = createRestTemplate();
    }

    public double getUsdToInrRate() {

        // 1️⃣ Check Redis
        Object cachedRate = redisTemplate.opsForValue().get(REDIS_KEY);
        if (cachedRate instanceof Double) {
            return (Double) cachedRate;
        }

        System.out.println("➡ Fetching USD→INR from FX API");
        // 2️⃣ Call FX API
        FxRatesResponse response =
                restTemplate.getForObject(FX_API_URL, FxRatesResponse.class);

        if (response == null || response.getRates() == null) {
            throw new IllegalStateException("FX API returned empty response");
        }

        Double usd = response.getRates().get("USD");
        Double inr = response.getRates().get("INR");

        if (usd == null || inr == null) {
            throw new IllegalStateException("USD/INR rate missing");
        }

        double usdToInr = inr / usd;

        // 3️⃣ Save in Redis
        redisTemplate.opsForValue()
                .set(REDIS_KEY, usdToInr, CACHE_TTL);

        return usdToInr;
    }

    private RestTemplate createRestTemplate() {
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return new RestTemplate(factory);
    }
}

package com.multiforecast.apirouter.controller;

import com.multiforecast.apirouter.grpc.ForecastClient;
import com.multiforecast.apirouter.model.Forecast;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForecastController {

    private final ForecastClient forecastClient;

    @GetMapping("forecast")
    public Forecast forecast(@RequestParam Long userId) {
        return forecastClient.getForecast(userId);
    }
}

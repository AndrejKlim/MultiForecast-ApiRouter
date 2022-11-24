package com.multiforecast.apirouter.grpc;

import com.multiforecast.apirouter.controller.UserNotFoundException;
import com.multiforecast.apirouter.model.Forecast;
import com.multiforecast.forecastservice.ForecastRequest;
import com.multiforecast.forecastservice.ForecastResponse;
import com.multiforecast.forecastservice.ForecastServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class ForecastClient {

    @Value("${app.grpc.forecastService.host}")
    private String forecastServiceHost;
    @Value("${app.grpc.forecastService.port}")
    private Integer forecastServicePort;

    public Forecast getForecast(Long userId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(forecastServiceHost, forecastServicePort)
                .usePlaintext()
                .build();
        final ForecastServiceGrpc.ForecastServiceBlockingStub forecastServiceClient = ForecastServiceGrpc.newBlockingStub(channel);

        Optional<ForecastResponse> forecastResponse = Optional.empty();
        try {
            forecastResponse = ofNullable(forecastServiceClient.getForecast(ForecastRequest.newBuilder().setUserId(userId).build()));
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new UserNotFoundException("User not found. Check your id or register");
            }
            log.error("No user found. {}", e.getMessage());
        }

        channel.shutdown();
        log.info("Returning Forecast response {}", forecastResponse.map(ForecastResponse::getForecast).orElse(null));
        return forecastResponse.map(r -> new Forecast(r.getForecast())).orElse(null);
    }
}

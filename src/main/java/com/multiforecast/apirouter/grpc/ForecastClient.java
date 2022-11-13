package com.multiforecast.apirouter.grpc;

import com.multiforecast.apirouter.model.Forecast;
import com.multiforecast.forecastservice.ForecastRequest;
import com.multiforecast.forecastservice.ForecastResponse;
import com.multiforecast.forecastservice.ForecastServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class ForecastClient {

    public Forecast getForecast(Long userId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        final ForecastServiceGrpc.ForecastServiceBlockingStub forecastServiceClient = ForecastServiceGrpc.newBlockingStub(channel);

        ForecastResponse forecastResponse = forecastServiceClient.getForecast(ForecastRequest.newBuilder().setUserId(userId).build());

        return new Forecast(forecastResponse.getForecast());
    }
}

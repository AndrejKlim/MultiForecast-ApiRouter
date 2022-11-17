package com.multiforecast.apirouter.grpc;

import com.multiforecast.apirouter.model.LocationSaveRequest;
import com.multiforecast.userservice.UserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@GRpcService
@RequiredArgsConstructor
public class UserClient {

    @Value("${app.grpc.userService.host}")
    private String userServiceHost;
    @Value("${app.grpc.userService.port}")
    private Integer userServicePort;

    public void saveLocation(LocationSaveRequest request) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(userServiceHost, userServicePort)
                .usePlaintext()
                .build();

        final UserServiceGrpc.UserServiceBlockingStub userServiceClient = UserServiceGrpc.newBlockingStub(channel);

        userServiceClient.saveLocation(com.multiforecast.userservice.LocationSaveRequest
                .newBuilder()
                .setUserId(request.userId())
                .setLat(request.latitude())
                .setLon(request.longitude())
                .build());

        channel.shutdown();
    }
}

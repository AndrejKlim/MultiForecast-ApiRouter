package com.multiforecast.apirouter.controller;

import com.multiforecast.apirouter.grpc.UserClient;
import com.multiforecast.apirouter.model.LocationSaveRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LocationController {

    private final UserClient userClient;

    @PostMapping("location")
    public void saveLocation(@RequestBody LocationSaveRequest saveRequest){
        log.info("Recieved {}", saveRequest);
        userClient.saveLocation(saveRequest);
        log.info("Saved user location {}", saveRequest);
    }
}

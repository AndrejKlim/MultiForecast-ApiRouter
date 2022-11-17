package com.multiforecast.apirouter.model;

public record LocationSaveRequest(Long userId, String latitude, String longitude) {
}

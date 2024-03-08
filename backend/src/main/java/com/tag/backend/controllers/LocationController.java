package com.tag.backend.controllers;

import com.tag.backend.model.Ip2LocationResponse;
import com.tag.backend.model.LocationPayload;
import com.tag.backend.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @Autowired
    LocationService locationService;

    @PostMapping("/tag/loc")
    public String receiveLocation(@RequestBody LocationPayload payload) {
        String ip = locationService.getClientIp();
        String googleMapsUrl = "https://www.google.com/maps?q=" + payload.getLatitude() + "," + payload.getLongitude();
        Ip2LocationResponse data = locationService.ipData(ip);
        String googleMapsUrl1 = "https://www.google.com/maps?q=" + data.getLatitude() + "," + data.getLongitude();
        return "Based on long/lat: " + googleMapsUrl + "\nBased on IP: " + googleMapsUrl1;
    }
}

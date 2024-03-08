package com.tag.backend.controllers;

import com.tag.backend.model.LocationPayload;
import com.tag.backend.services.LocationService;
import com.tag.backend.utils.UtilsFile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class LocationController {

    @Autowired
    LocationService locationService;

    @PostMapping("tag/loc")
    public String receiveLocation(@RequestBody LocationPayload payload) {
        String ip =locationService.getClientIp();
        System.out.println(payload + " "+ ip);
        return "Received location: " + payload.getLatitude() + ", " + payload.getLongitude() ;
    }
}

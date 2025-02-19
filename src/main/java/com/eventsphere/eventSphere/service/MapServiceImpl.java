package com.eventsphere.eventSphere.service;

import com.eventsphere.eventSphere.service.MapService;
import com.eventsphere.eventSphere.config.GoogleMapsConfig;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {

    private final GoogleMapsConfig config;

    public MapServiceImpl(GoogleMapsConfig config) {
        this.config = config;
    }

    @Override
    public String getGoogleMapsApiKey() {
        return config.getApiKey();
    }
}

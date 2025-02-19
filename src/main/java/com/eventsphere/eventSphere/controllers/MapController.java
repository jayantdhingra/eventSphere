package com.eventsphere.eventSphere.controllers;

import com.eventsphere.eventSphere.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maps")
public class MapController {
    @Autowired
    private MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/api-key")
    public String getApiKey() {
        return mapService.getGoogleMapsApiKey();
    }
}

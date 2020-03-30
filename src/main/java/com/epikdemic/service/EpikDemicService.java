package com.epikdemic.service;

import com.epikdemic.dto.AppRequest;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TransitMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class EpikDemicService {

    public List<String> appRequest(AppRequest appRequest) throws InterruptedException, ApiException, IOException {
        GeoApiContext geoApiContext = new GeoApiContext.Builder().apiKey("AIzaSyAh116e2yFaZA71RLwvu-4xwurB3A5xfCs").build();
        String origin = appRequest.getStartLatitude()+","+appRequest.getStartLongitude();
        String destination = appRequest.getEndLatitude()+","+appRequest.getEndLongitude();
        DirectionsResult directionsResult = null;
        if(!appRequest.getTransitMode().equals("NOT_SPECIFIED")) {
            directionsResult = new DirectionsApiRequest(geoApiContext).
                    transitMode(TransitMode.valueOf(appRequest.getTransitMode())).origin(origin).destination(destination).await();
        } else {
            directionsResult = new DirectionsApiRequest(geoApiContext).
                    origin(origin).destination(destination).await();
        }
        if(directionsResult == null){
            throw new RuntimeException();
        }
        DirectionsRoute directionsRoutes[] = directionsResult.routes;
        DirectionsRoute directionsRoute = directionsRoutes[0];
        List<String> routeLatLongList= new ArrayList<>();

        IntStream.range(0, directionsRoute.legs.length).forEach(
                n -> {
                    IntStream.range(0, directionsRoute.legs[n].steps.length).forEach(
                            m -> {
                                routeLatLongList.add(String.valueOf(directionsRoute.legs[n].steps[m].startLocation.lat)
                                         + "," + String.valueOf(directionsRoute.legs[n].steps[m].startLocation.lng));
                            }
                    );
                }
        );
        routeLatLongList.add(String.valueOf(directionsRoute.
                legs[directionsRoute.legs.length - 1].steps[directionsRoute.legs[directionsRoute.legs.length - 1].steps.length - 1].endLocation.lat)
                + "," + String.valueOf(directionsRoute.legs[directionsRoute.legs.length - 1].steps[directionsRoute.legs[directionsRoute.legs.length -1 ].steps.length - 1].endLocation.lng));

        log.info("result is {}", routeLatLongList);
        return routeLatLongList;
    }
}

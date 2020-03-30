package com.epikdemic.mappers;


import com.epikdemic.dto.AppResponse;
import com.epikdemic.dto.Location;

import java.util.ArrayList;
import java.util.List;

public class mapper {
    public static AppResponse mock(List<String> list){
        List<Location> locationList = new ArrayList<>();
        for (String location: list ){
            Location locat = new Location();
            String[] value = location.split(",");
            locat.setLatitude(Double.parseDouble(value[0]));
            locat.setLongitude(Double.parseDouble(value[1]));
            locat.setLocationScore(7.0);
            locat.setRadius(.1);
            locationList.add(locat);
        }
        AppResponse appResponse = AppResponse.builder().overallScore(5.0).locationList(locationList).build();
        return appResponse;
    }
}

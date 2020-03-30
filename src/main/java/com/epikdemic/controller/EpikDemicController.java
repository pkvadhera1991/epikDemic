package com.epikdemic.controller;

import com.epikdemic.dto.AppRequest;
import com.epikdemic.dto.AppResponse;
import com.epikdemic.dto.StartScoringRequest;
import com.epikdemic.mappers.mapper;
import com.epikdemic.retrofit.StartScoringGateway;
import com.epikdemic.service.EpikDemicService;
import com.google.maps.errors.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/request")
public class EpikDemicController {

    private final EpikDemicService epikDemicService;
    private final StartScoringGateway startScoringGateway;

    public EpikDemicController(EpikDemicService epikDemicService, StartScoringGateway startScoringGateway) {
        this.epikDemicService = epikDemicService;
        this.startScoringGateway = startScoringGateway;
    }

    @PostMapping("/")
    public ResponseEntity<AppResponse> appRequest(@RequestBody AppRequest appRequest) {
        List<String> list = new ArrayList<>();
        try {
            list = epikDemicService.appRequest(appRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return new ResponseEntity<AppResponse>(mapper.mock(list), HttpStatus.OK);

        return new ResponseEntity<AppResponse>(startScoringGateway
                .startScoring(StartScoringRequest.builder()
                        .list(list)
                        .transitMode(appRequest.getTransitMode())
                        .build()), HttpStatus.OK);


    }
}

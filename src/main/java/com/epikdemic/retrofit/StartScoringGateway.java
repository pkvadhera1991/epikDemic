package com.epikdemic.retrofit;

import com.epikdemic.dto.AppResponse;
import com.epikdemic.dto.HttpConfig;
import com.epikdemic.dto.StartScoringRequest;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class StartScoringGateway {
    static final String JSON_CONTENT_TYPE = "application/json";
    private final StartScoringInterface service;

    public StartScoringGateway(HttpConfig<StartScoringInterface> httpConfig) {
        this.service = httpConfig.getHttpService(StartScoringInterface.class);;
    }

    public AppResponse startScoring(StartScoringRequest startScoringRequest) {
        Call<AppResponse> retrofitCall = service.startScoring(startScoringRequest, JSON_CONTENT_TYPE);
        Response<AppResponse> response = null;
        try {
            log.info("executing retrofit call for {}", startScoringRequest);
            response = retrofitCall.execute();
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

        if (!response.isSuccessful()) {
            if (response.errorBody() != null) {
                log.error("Error response from ML: {}", response.errorBody().toString() );
                throw new RuntimeException(response.errorBody().toString() );
            }
            log.error("Call failed, unknown error");
            throw new RuntimeException("Call failed, unknown error");
        }
        log.info(response.body().toString());
        return  response.body();
    }
}

package com.epikdemic.retrofit;

import com.epikdemic.dto.AppResponse;
import com.epikdemic.dto.StartScoringRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StartScoringInterface {
    @POST("contagion_risk/")
    Call<AppResponse> startScoring(
            @Body StartScoringRequest requestBody,
            @Header("Content-Type") String contentType);
}

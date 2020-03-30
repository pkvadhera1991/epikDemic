package com.epikdemic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppRequest {
    Double startLatitude;
    Double startLongitude;
    Double endLatitude;
    Double endLongitude;
    String transitMode;
}

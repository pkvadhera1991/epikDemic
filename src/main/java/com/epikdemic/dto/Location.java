package com.epikdemic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    Double locationScore;
    Double radius;
    Double latitude;
    Double longitude;
}

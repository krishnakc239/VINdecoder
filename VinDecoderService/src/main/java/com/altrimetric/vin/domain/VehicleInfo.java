package com.altrimetric.vin.domain;

import lombok.Data;

@Data
public class VehicleInfo {
    private String  make;
    private String  model;
    private String  modelYear;
    private String  country;
    private String  city;
    private String  state;
}

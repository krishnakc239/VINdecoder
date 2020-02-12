package com.altrimetric.vin.service;

import com.altrimetric.vin.domain.Vehicle;
import com.altrimetric.vin.domain.VehicleInfo;

public interface VinDecoderService {

    public VehicleInfo getVehicleInfo(String vin);
}

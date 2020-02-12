package com.altrimetric.vin.controller;


import com.altrimetric.vin.domain.VehicleInfo;
import com.altrimetric.vin.service.VinDecoderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/vehicle")
@Api(value="vehicle info ", description="VEhichel info based on VIN")
@CrossOrigin
public class VinController {


    @Autowired
    private VinDecoderService vinDecoderService;

    @RequestMapping(value = "/{vin}", produces = "application/json")
    public ResponseEntity<VehicleInfo> getVehicleInfo(@PathVariable String vin){
        VehicleInfo vehicleInfo = vinDecoderService.getVehicleInfo(vin);
        HttpStatus status = (vehicleInfo !=null && vehicleInfo.getModel() !=null)? HttpStatus.CREATED: HttpStatus.BAD_GATEWAY;
        return new ResponseEntity<>(vehicleInfo,status);
    }

}

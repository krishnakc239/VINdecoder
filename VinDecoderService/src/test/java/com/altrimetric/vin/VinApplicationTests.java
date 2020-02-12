package com.altrimetric.vin;

import com.altrimetric.vin.domain.VehicleInfo;
import com.altrimetric.vin.service.VinDecoderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@SpringBootTest
class VinApplicationTests {

    @Autowired
    private VinDecoderService vinDecoderService;


    @Test
    public void checkVehicle(){
        VehicleInfo actualVehicle = new VehicleInfo();
        VehicleInfo expectedVehicle = new VehicleInfo();
        expectedVehicle.setMake("ACURA");
        expectedVehicle.setModel(null);
        expectedVehicle.setModelYear("2012");
        expectedVehicle.setCountry("JAPAN");
        expectedVehicle.setState("SAITAMA");
        expectedVehicle.setCity("SAYAMA");

        actualVehicle =vinDecoderService.getVehicleInfo("JH4TB2H26CC000000");

        assertEquals(actualVehicle,expectedVehicle);
    }



}

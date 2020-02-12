package com.altrimetric.vin.service;

import com.altrimetric.vin.domain.Info;
import com.altrimetric.vin.domain.Vehicle;
import com.altrimetric.vin.domain.VehicleInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class VinDecoderServiceImpl implements VinDecoderService {

    //JH4TB2H26CC000000
    private final static String BASE_API_URL ="https://vpic.nhtsa.dot.gov/api/vehicles/DecodeVinExtended/";

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public VehicleInfo getVehicleInfo(String vin) {
        ResponseEntity<Object> response =
                restTemplate.exchange(BASE_API_URL+vin+"?format=json",
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {});

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        System.out.println(response);

        Predicate<Info> checkInfo = (info -> {
            if (    info.getVariable().equalsIgnoreCase("Make")||
                    info.getVariable().equalsIgnoreCase("Model")||
                    info.getVariable().equalsIgnoreCase("Model Year")||
                    info.getVariable().equalsIgnoreCase("Plant City")||
                    info.getVariable().equalsIgnoreCase("Plant State")||
                    info.getVariable().equalsIgnoreCase("Plant Country")) {
                return true;
            }
            return false;
        });
        Vehicle vehicle = new Vehicle();
        VehicleInfo vehicleInfo =new VehicleInfo();
        if (response.getStatusCode() == HttpStatus.OK){
            System.out.println("status =="+ response.getStatusCode());
            vehicle = mapper.convertValue(response.getBody(),Vehicle.class);
            List<Info> infos = vehicle.getInfoList().stream().filter(checkInfo).collect(Collectors.toList());
            vehicle.setInfoList(infos);

            for (Info in : vehicle.getInfoList()){
                if (in.getVariable().equalsIgnoreCase("Make")) vehicleInfo.setMake(in.getValue());
                if (in.getVariable().equalsIgnoreCase("Model Year")) vehicleInfo.setModelYear(in.getValue());
                if (in.getVariable().equalsIgnoreCase("Model")) vehicleInfo.setModel(in.getValue());
                if (in.getVariable().equalsIgnoreCase("Plant City")) vehicleInfo.setCity(in.getValue());
                if (in.getVariable().equalsIgnoreCase("Plant State")) vehicleInfo.setState(in.getValue());
                if (in.getVariable().equalsIgnoreCase("Plant Country")) vehicleInfo.setCountry(in.getValue());

            }
        }
        return vehicleInfo;
    }


}


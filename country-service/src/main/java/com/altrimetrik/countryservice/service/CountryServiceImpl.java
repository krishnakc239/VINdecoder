package com.altrimetrik.countryservice.service;

import com.altrimetrik.countryservice.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CountryServiceImpl implements CountryService {
    final static String BASE_URL ="http://api.worldbank.org/country?format=json";
    @Autowired
    private  RestTemplate restTemplate;

    private  List<Country> getCountryList(){
        /**exchange : Executes a specified HTTP method against a URL, returning a ResponseEntity containing an object mapped from the response body
         * getForObject : httpstatus can be maintained*/
        ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET,null,String.class);

        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(responseEntity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONArray countryDataArray = (JSONArray)jsonArray.get(1);
        List<Country> countryList = null;
        CollectionType typeReference =
                TypeFactory.defaultInstance().constructCollectionType(List.class, Country.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            countryList = objectMapper.readValue(countryDataArray.toString(),typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return countryList;
    }
    @Override
    public ResponseEntity<Country> getCountry(String code) {

        List<Country> countryList = getCountryList();
        Country country = null;
        for (Country c : countryList){
//            System.out.println(c.getId());
            if (c.getId().equals(code)){
                country = c;
                break;
            }
        }
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (country!=null){
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(country,status);
    }

    public ResponseEntity<List<Country>> getAll(){

        List<Country> countryList = getCountryList();
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (!countryList.isEmpty()){
            status = HttpStatus.OK;
        }
        System.out.println("Status getaALl == "+ status);
        return new ResponseEntity<>(countryList,status);
    }

    @Override
    public ResponseEntity<Set<Country>> searchCountry(String code, boolean region, boolean incomeLevel, boolean lendingType) {

        if (code != null && !code.isEmpty()){

        }
        ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL+"?format=json", HttpMethod.GET,null,String.class);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = null;
        try {
            jsonArray = (JSONArray) jsonParser.parse(responseEntity.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Set<Country> countries = new HashSet<>();
        JSONArray countriesJsonData = (JSONArray)jsonArray.get(1);

        for (int i = 0; i< countriesJsonData.size();i++){
            JSONObject jsonObject = (JSONObject) countriesJsonData.get(i);
//            if (jsonObject.get("id").equals(code)){
//                System.out.println("code matchedd !!!");
//                Country country = new Country();
//                country.setCode((String) jsonObject.get("id"));
//                country.setName((String) jsonObject.get("name"));
//                country.setCapitalCity((String) jsonObject.get("capitalCity"));
//                country.setLatitude(Double.valueOf(String.valueOf(jsonObject.get("latitude"))));
//                country.setLongitude(Double.valueOf(String.valueOf(jsonObject.get("longitude"))));
//            }
            JSONObject  regionObject = (JSONObject)jsonObject.get("region");
            if (regionObject.get("id").equals(region)){
                Country country = updateCountry(jsonObject);
                countries.add(country);
            }
            JSONObject  incomeObject = (JSONObject)jsonObject.get("incomeLevel");
            if (incomeObject.get("id").equals(incomeLevel)){
               Country country = updateCountry(jsonObject);
                countries.add(country);
            }


        }
        return new ResponseEntity<>(countries,HttpStatus.OK);
    }

    public Country updateCountry(JSONObject jsonObject){
        String code = String.valueOf(jsonObject.get("id"));
        String name = String.valueOf(jsonObject.get("name"));
        String capitalCity = String.valueOf(jsonObject.get("capitalCity"));
        String lat = String.valueOf(jsonObject.get("longitude"));
        String lon = String.valueOf(jsonObject.get("latitude"));
        System.out.println("lat == "+ lat +", longitude == "+ lon);

        Country country = new Country();
        country.setName(name);
//        country.setCode(code);
//        country.setCapitalCity(capitalCity);
//        country.setLatitude(Double.valueOf(StringUtils.isEmpty(lat) ? "0.00": lat));
//        country.setLongitude(Double.valueOf(StringUtils.isEmpty(lon) ? "0.00": lon));

        return country;
    }
}

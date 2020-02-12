package com.altrimetrik.countryservice.service;

import com.altrimetrik.countryservice.model.Country;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface CountryService {

    public ResponseEntity<Country> getCountry(String code);
    public ResponseEntity<List<Country>> getAll();


    public ResponseEntity<Set<Country>> searchCountry(String code, boolean region, boolean incomeLevel, boolean lendingType);


}

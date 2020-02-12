package com.altrimetrik.countryservice.model;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class Country {

    String id;
    String iso2Code;
    String name;
    String capitalCity;
    Map<String, String> region = new HashMap<>();
    Map<String, String> adminregion = new HashMap<>();
    Map<String, String> incomeLevel = new HashMap<>();
    Map<String, String> lendingType = new HashMap<>();
    Double latitude;
    Double longitude;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return getId().equals(country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

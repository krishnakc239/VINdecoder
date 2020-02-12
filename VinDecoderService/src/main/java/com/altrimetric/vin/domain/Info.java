package com.altrimetric.vin.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Info {

    /**
     * "Value": "JH4!B2H26CC000000",
     *             "ValueId": "",
     *             "Variable": "Suggested VIN",
     *             "VariableId": 142*/
    @JsonProperty("Value")
    private String value;
    @JsonProperty("ValueId")
    private  String valueId;
    @JsonProperty("Variable")
    private  String variable;
    @JsonProperty("VariableId")
    private  String variableId;
}

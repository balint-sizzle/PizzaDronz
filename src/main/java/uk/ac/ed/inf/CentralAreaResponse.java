package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CentralAreaResponse {
    @JsonProperty("name")
    public String name;

    @JsonProperty("longitude")
    public double longitude;

    @JsonProperty("latitude")
    public double latitude;
}

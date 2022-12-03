package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Menu items from restaurants
 */
public class Menu {
    @JsonProperty("name")
    String name;
    @JsonProperty("priceInPence")
    int priceInPence;
}

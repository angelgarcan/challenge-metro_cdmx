package com.garcan.subway.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Itinerary {

  private List<Route> segments = new ArrayList<>();

}

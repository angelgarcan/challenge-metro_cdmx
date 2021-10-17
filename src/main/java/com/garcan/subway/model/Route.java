package com.garcan.subway.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Route {
  /** Length of the route, measured in the number of edges. **/
  private double lenght;
  private List<String> path;
}

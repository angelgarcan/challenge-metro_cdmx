package com.garcan.subway.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class MetroMap {
  private final List<Line> lines;
  private final Map<String, Station> index;

}

package com.garcan.subway.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Line {
  private String id;
  private String name;
  private List<Station> stations = new ArrayList<>();

  public Line(final String name) {
    this.name = name;
    this.id = name.substring(name.lastIndexOf(' ') + 1);
  }
}

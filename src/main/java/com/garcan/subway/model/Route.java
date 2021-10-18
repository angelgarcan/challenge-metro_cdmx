package com.garcan.subway.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Route {
  private List<String> path;
  private String direction;

  public String pretty() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("From \"");
    stringBuilder.append(this.path.get(0));
    stringBuilder.append("\" go direction to \"");
    stringBuilder.append(this.direction);
    stringBuilder.append("\" for ");
    stringBuilder.append(this.path.size());
    stringBuilder.append(" stations until \"");
    stringBuilder.append(this.path.get(this.path.size() - 1));
    stringBuilder.append("\" :: ");
    stringBuilder.append(this.path.toString());
    stringBuilder.append("<br>\n");
    return stringBuilder.toString();
  }
}

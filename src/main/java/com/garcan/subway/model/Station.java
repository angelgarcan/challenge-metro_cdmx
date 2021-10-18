package com.garcan.subway.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Station {
  private String id;
  private String name;
  @Builder.Default
  private List<String> belongsLines = new ArrayList<>();
  private String description;
  @NonNull
  @EqualsAndHashCode.Include
  private Point point;

  public boolean belongsTo(final String lineName) {
    return this.belongsLines.contains(lineName);
  }
}

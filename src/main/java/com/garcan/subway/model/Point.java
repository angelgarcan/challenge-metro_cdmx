package com.garcan.subway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@Builder
public class Point {
  private Double longitude;
  private Double latitude;

  public double euclidean(final Point p) {
    return Math.sqrt(
        (p.longitude - this.longitude) * (p.longitude - this.longitude)
            + (p.latitude - this.latitude) * (p.latitude - this.latitude));
  }

  public boolean isNearby(final Point p, final double maxTolerance) {
    return euclidean(p) <= maxTolerance;
  }
}

package com.garcan.subway.model;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MetroGraph {
  private final Graph<String, DefaultEdge> graph;
}

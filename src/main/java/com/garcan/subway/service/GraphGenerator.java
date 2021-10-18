package com.garcan.subway.service;

import java.util.List;
import javax.annotation.PostConstruct;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import com.garcan.subway.model.Line;
import com.garcan.subway.model.MetroGraph;
import com.garcan.subway.model.MetroMap;
import com.garcan.subway.model.Station;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GraphGenerator {

  @Autowired
  private MetroMap metroMap;

  @Bean(name = "metroGraph")
  @Scope("singleton")
  @PostConstruct
  public MetroGraph graph() {
    log.info("Generating graph...");
    final Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    final List<Line> lines = this.metroMap.getLines();

    for (final Line line : lines) {
      final List<Station> stations = line.getStations();
      Station previous = null;
      for (int i = 0; i < stations.size(); i++) {
        final Station current = stations.get(i);
        if (!graph.containsVertex(current.getName())) {
          graph.addVertex(current.getName());
        }
        if (previous != null) {
          final DefaultEdge edge = graph.addEdge(previous.getName(), current.getName());
          /* TODO: Distance between stations as weight */
          // g.setEdgeWeight(edge, previous.getPoint().euclidean(current.getPoint()));
        }
        previous = current;
      }
    }
    return new MetroGraph(graph);
  }
}

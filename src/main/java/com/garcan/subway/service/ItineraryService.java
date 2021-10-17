package com.garcan.subway.service;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garcan.subway.model.MetroGraph;
import com.garcan.subway.model.Route;

@Service
public class ItineraryService {

  @Autowired
  private MetroGraph subwayGraph;

  // TODO: terminalStation
  // TODO: belongSameLine
  // TODO: extractSubpath
  // TODO: generateItinerary

  public Route getRoute(final String start, final String end) {
    final SingleSourcePaths<String, DefaultEdge> iPaths =
        new DijkstraShortestPath<>(this.subwayGraph.getGraph()).getPaths(start);
    final GraphPath<String, DefaultEdge> routeGraph = iPaths.getPath(end);
    return new Route(routeGraph.getLength(), routeGraph.getVertexList());
  }
}

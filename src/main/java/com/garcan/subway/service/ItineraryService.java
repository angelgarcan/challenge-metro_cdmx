package com.garcan.subway.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.garcan.subway.exception.own.ServiceException;
import com.garcan.subway.model.Itinerary;
import com.garcan.subway.model.Line;
import com.garcan.subway.model.MetroGraph;
import com.garcan.subway.model.MetroMap;
import com.garcan.subway.model.Route;
import com.garcan.subway.model.Station;

@Service
public class ItineraryService {

  @Autowired
  private MetroGraph metroGraph;
  @Autowired
  private MetroMap metroMap;

  private Route getSegment(final String stationAName, final String stationBName) {
    if (stationAName == null || stationBName == null || stationAName.equals(stationBName)) {
      throw new ServiceException("Wrong params.");
    }

    final Station stationA = this.metroMap.getIndex().get(stationAName);
    final Station stationB = this.metroMap.getIndex().get(stationBName);

    final Line commonLine = sameLine(stationA, stationB);
    if (commonLine == null) { /* Stations not in the same line. */
      return null;
    }

    final List<Station> stations = commonLine.getStations();
    final int idxA = stations.indexOf(stationA);
    final int idxB = stations.indexOf(stationB);

    Station terminalStation;
    List<Station> subPath;
    if (idxA > idxB) {
      terminalStation = stations.get(0);
      subPath = new ArrayList<>(stations.subList(idxB, idxA + 1));
      Collections.reverse(subPath);
    } else {
      terminalStation = stations.get(stations.size() - 1);
      subPath = new ArrayList<>(stations.subList(idxA, idxB + 1));
    }

    return new Route(
        subPath.stream().map(Station::getName).collect(Collectors.toList()),
        terminalStation.getName());
  }

  public Itinerary generateItinerary(final String start, final String end) {
    final Itinerary itinerary = new Itinerary();
    final Route fullRoute = getDijkstraRoute(start, end);
    final List<String> fullPath = fullRoute.getPath();

    String previousTransfer = fullPath.get(0);
    String previous = null;
    for (final String current : fullPath) {
      if (previous != null) {
        final Line commonLine = commonLine(previousTransfer, current);
        if (commonLine == null) { // Continue until found a transfer.
          itinerary.getSegments().add(getSegment(previousTransfer, previous));
          previousTransfer = previous;
        }
      }
      previous = current;
    }
    itinerary.getSegments().add(
        getSegment(previousTransfer, fullPath.get(fullPath.size() - 1)));
    return itinerary;
  }

  private Line commonLine(final String stationAName, final String stationBName) {
    return sameLine(this.metroMap.getIndex().get(stationAName),
        this.metroMap.getIndex().get(stationBName));
  }

  private Line sameLine(final Station stationA, final Station stationB) {
    final String lineName = stationA.getBelongsLines().stream()
        .filter(stationB.getBelongsLines()::contains)
        .findFirst()
        .orElse(null);

    return this.metroMap.getLines().stream()
        .filter(o -> o.getName().equals(lineName))
        .findFirst()
        .orElse(null);
  }

  public Route getDijkstraRoute(final String start, final String end) {
    final SingleSourcePaths<String, DefaultEdge> iPaths =
        new DijkstraShortestPath<>(this.metroGraph.getGraph()).getPaths(start);
    final GraphPath<String, DefaultEdge> routeGraph = iPaths.getPath(end);
    return new Route(routeGraph != null ? routeGraph.getVertexList() : new ArrayList<String>(),
        null);
  }
}

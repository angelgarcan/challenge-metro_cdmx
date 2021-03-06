package com.garcan.subway.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.w3c.dom.Node;
import com.garcan.subway.exception.own.ServiceException;
import com.garcan.subway.model.Line;
import com.garcan.subway.model.MetroMap;
import com.garcan.subway.model.Point;
import com.garcan.subway.model.Station;
import com.garcan.subway.utils.XmlManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class KmlScanner {

  private static final String DEFAULT_KML = "Metro_CDMX.kml";
  private static final double MAX_TOLERANCE_DISTANCE = 0.001505756658423248;

  private final XmlManager xmlManager = new XmlManager();

  @Bean(name = "metroMap")
  @Scope("singleton")
  public MetroMap parseKml() throws ServiceException {
    try {
      log.info("Parsing KML file: " + new File(DEFAULT_KML).getAbsolutePath());
      this.xmlManager.loadXML(DEFAULT_KML, "UTF-8");

      final Map<String, Station> stationsIndex = parseStations();
      final List<Line> lines = parseLines(new ArrayList<>(stationsIndex.values()));

      return new MetroMap(lines, stationsIndex);
    } catch (final Exception e) {
      throw new ServiceException("Parsing KML file.", e);
    }
  }

  private List<Line> parseLines(final List<Station> stations) throws XPathExpressionException {
    final ArrayList<Line> linesList = new ArrayList<>();
    final List<Node> linesNodesList = this.xmlManager.toList(
        this.xmlManager.getXPathResult("/kml/Document/Folder[1]/Placemark",
            XPathConstants.NODESET));

    for (final Node lineNode : linesNodesList) {
      final String lineName =
          this.xmlManager.getXPathResultFromNode(lineNode, "./name");
      final Line line = new Line(lineName);

      final String allCoordString =
          this.xmlManager.getXPathResultFromNode(lineNode, "./LineString/coordinates");
      final List<Point> lineCoordinates =
          Arrays.stream(allCoordString.trim().split(" +"))
              .map(String::trim)
              .map(this::splitPoint)
              .collect(Collectors.toList());
      for (int i = 0; i < lineCoordinates.size(); i++) {
        final Station station = look4Station(stations, lineCoordinates.get(i));
        if (station == null) {
          continue;
        }

        station.getBelongsLines().add(line.getName());

        final String stationId = line.getId() + "-" + (i + 1);
        if (station.getId() == null) {
          station.setId(stationId);
        } else {
          station.setId(station.getId() + "," + stationId);
        }

        line.getStations().add(station);
      }
      linesList.add(line);
    }
    return linesList;
  }

  private HashMap<String, Station> parseStations() throws XPathExpressionException {
    final HashMap<String, Station> stationsIndexLocal = new HashMap<>();
    final List<Node> stationsNodesList = this.xmlManager.toList(
        this.xmlManager.getXPathResult("/kml/Document/Folder[2]/Placemark",
            XPathConstants.NODESET));

    for (final Node stationNode : stationsNodesList) {
      final String stationName =
          this.xmlManager.getXPathResultFromNode(stationNode, "./name");
      final String stationDesc =
          this.xmlManager.getXPathResultFromNode(stationNode, "./description");
      final Point stationPoint =
          splitPoint(this.xmlManager.getXPathResultFromNode(stationNode, "./Point/coordinates"));
      stationsIndexLocal.put(stationName, Station.builder()
          .name(stationName)
          .description(stationDesc)
          .point(stationPoint)
          .build());
    }
    return stationsIndexLocal;
  }

  private Station look4Station(final List<Station> stations, final Point coordinates) {
    return look4Station(stations, coordinates, MAX_TOLERANCE_DISTANCE);
  }

  private Station look4Station(final List<Station> stations, final Point point,
      final Double maxTolerance) {
    final int stationIdx = stations.indexOf(new Station(point));

    /* IF: There is not an exact match */
    if (stationIdx < 0 && maxTolerance != null && maxTolerance > 0) {
      /* THEN: Returns the nearest station */
      return stations.stream()
          .filter(s -> s.getPoint().isNearby(point, maxTolerance))
          .findFirst()
          .orElseGet(() -> {
            log.error("Coordinates " + point + " has not been found.");
            return null;
          });
    }
    return stations.get(stationIdx);
  }


  private Point splitPoint(final String coordinatesString) {
    final String[] longLat = coordinatesString.trim().split(",", 3);
    try {
      return Point.builder()
          .longitude(Double.valueOf(longLat[0]))
          .latitude(Double.valueOf(longLat[1]))
          .build();
    } catch (final NumberFormatException e) {
      throw new ServiceException("Converting string to Point: " + coordinatesString);
    }
  }
}

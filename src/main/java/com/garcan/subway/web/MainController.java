package com.garcan.subway.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.garcan.subway.model.Itinerary;
import com.garcan.subway.model.MetroMap;
import com.garcan.subway.model.Route;
import com.garcan.subway.service.ItineraryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {

  @Autowired
  private MetroMap metroMap;
  @Autowired
  private ItineraryService itineraryService;

  @GetMapping("/map/list")
  public MetroMap mapList() throws Exception {
    log.debug("Listing map...");
    return this.metroMap;
  }

  @GetMapping("/map/index")
  public List<String> mapIndex() throws Exception {
    log.debug("Listing stations index...");
    return new ArrayList<>(this.metroMap.getIndex().values()).stream()
        .map(s -> s.getName() + " -> " + s.getId()).collect(Collectors.toList());
  }

  @GetMapping("/map/print")
  public String mapPrint() throws Exception {
    log.debug("Printing map...");
    // TODO: Code here.
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  @GetMapping("/route/get")
  public Route routeGet(@RequestParam final String start, @RequestParam final String end)
      throws Exception {
    log.debug("Getting route...");
    final Route route = this.itineraryService.getDijkstraRoute(start, end);
    return route;
  }

  @GetMapping("/itinerary/get")
  public Itinerary itineraryGet(@RequestParam final String start, @RequestParam final String end)
      throws Exception {
    log.debug("Getting itinerary...");
    return this.itineraryService.generateItinerary(start, end);
  }

  @GetMapping("/itinerary/pretty")
  public String itineraryPretty(@RequestParam final String start, @RequestParam final String end)
      throws Exception {
    log.debug("Printing pretty itinerary...");
    final Itinerary itinerary = this.itineraryService.generateItinerary(start, end);
    final StringBuilder prettyItinerary = new StringBuilder();
    prettyItinerary.append("Starting at \"");
    prettyItinerary.append(start);
    prettyItinerary.append("\" follow the next steps:<br>\n");
    for (final Route segment : itinerary.getSegments()) {
      prettyItinerary.append("   ");
      prettyItinerary.append(segment.pretty());
    }
    prettyItinerary.append("You have arrived to \"");
    prettyItinerary.append(end);
    prettyItinerary.append("\" !!!<br><br>\n\n");
    prettyItinerary.append(this.itineraryService.getDijkstraRoute(start, end).toString());
    return prettyItinerary.toString();
  }
}

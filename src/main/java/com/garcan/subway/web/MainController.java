package com.garcan.subway.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.garcan.subway.model.MetroMap;
import com.garcan.subway.model.Route;
import com.garcan.subway.service.ItineraryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {

  @Autowired
  private MetroMap subwayMap;
  @Autowired
  private ItineraryService itineraryService;



  @GetMapping("/map/list")
  public MetroMap mapList() throws Exception {
    log.debug("Listing map...");
    return this.subwayMap;
  }

  @GetMapping("/map/index")
  public List<String> mapIndex() throws Exception {
    log.debug("Listing stations index...");
    return new ArrayList<>(this.subwayMap.getIndex().values()).stream()
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
    final Route route = this.itineraryService.getRoute(start, end);
    return route;
  }

  @GetMapping("/itinerary/get")
  public String itineraryGet(@RequestParam final String start, @RequestParam final String end)
      throws Exception {
    log.debug("Getting itinerary...");
    // TODO: Code here.
    return start + " -> " + end;
  }
}

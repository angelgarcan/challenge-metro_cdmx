package com.garcan.subway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.garcan.subway.model.MetroMap;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {

  @Autowired
  private MetroMap subwayMap;

  @GetMapping("/map/list")
  public MetroMap mapList() throws Exception {
    log.debug("Listing map...");
    return this.subwayMap;
  }

  @GetMapping("/map/print")
  public String mapPrint() throws Exception {
    log.debug("Printing map...");
    // TODO: Code here.
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  @GetMapping("/itinerary/get")
  public String envioCorreo(@RequestParam final String start, @RequestParam final String end)
      throws Exception {
    log.debug("Getting itinerary...");
    // TODO: Code here.
    return start + " -> " + end;
  }
}

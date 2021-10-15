package com.garcan.subway.web;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MainController {

  @ExceptionHandler
  public void handleException() {
    //
  }

  @GetMapping("/map/list")
  public String mapList() {
    log.debug("Listing map...");
    // TODO: Code here.
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  @GetMapping("/map/print")
  public String mapPrint() {
    log.debug("Printing map...");
    // TODO: Code here.
    throw new UnsupportedOperationException("Not implemented yet!!!");
  }

  @GetMapping("/itinerary/get")
  public String envioCorreo(@RequestParam final String start, @RequestParam final String end) {
    log.debug("Getting itinerary...");
    // TODO: Code here.
    return start + " -> " + end;
  }
}

package com.garcan.subway.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
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
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GraphGenerator {

  public static final String GRAPH_FILE_PATH = "logs/metro_graph.png";

  @Autowired
  private MetroMap metroMap;

  @Bean(name = "metroGraph")
  @Scope("singleton")
  @PostConstruct
  public MetroGraph graph() throws IOException {
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
    log.info("Generating graph image (That may take a while)...");
    drawGraph(graph);
    return new MetroGraph(graph);
  }

  private <V, E> void drawGraph(final Graph<V, E> graph) throws IOException {
    final JGraphXAdapter<V, E> graphAdapter = new JGraphXAdapter<>(graph);
    final mxIGraphLayout layout = new mxFastOrganicLayout(graphAdapter);
    layout.execute(graphAdapter.getDefaultParent());
    final BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 1,
        Color.WHITE, false, null);
    final File imgFile = new File(GRAPH_FILE_PATH);
    ImageIO.write(image, "PNG", imgFile);
    log.info("Graph image saved to " + imgFile.getAbsolutePath() + ".");
  }

  public byte[] readGraphImage() throws IOException {
    return Files.readAllBytes(new File(GRAPH_FILE_PATH).toPath());
  }
}

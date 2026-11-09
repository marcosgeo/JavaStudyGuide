package chp14; 

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PathSizeApp {

  var text = new InputRea

  private long getSize(Path path) {
    try {
      return Files.size(path);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private long getPathSize(Path source) throws IOException {
    try (var s = Files.walk(source)) {
      return s.parallel()
          .filter(p -> !Files.isDirectory(p))
          .mapToLong(this::getSize)
          .sum();
    }
  }

  public static void main(String[] args) {
    PathSizeApp app = new PathSizeApp();
    try {
      double oneMB = 1024 * 1024 * 1.0;
      long size = app.getPathSize(args.length > 0 ? Path.of(args[0]) : Path.of("."));
      System.out.format("Total size: %.2f megabytes\n", (size / oneMB));
    } catch (IOException e) {
      System.err.println("Error occurred while calculating path size: " + e.getMessage());
    }
  }
}
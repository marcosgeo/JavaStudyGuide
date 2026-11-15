package chp14;

import java.io.*;
import java.util.*;

class GorillaApp {

  static void saveToFile(List<Gorilla> gorillas, File dataFile) throws IOException {
    try (
      var out = new ObjectOutputStream(new BufferedOutputStream(
        new FileOutputStream(dataFile)
      ));
    ) {
      for (Gorilla gorilla : gorillas){
        out.writeObject(gorilla);
      }
    }
  }

  static List<Gorilla> readFromFile(File dataFile) throws IOException, ClassNotFoundException {
    var gorillas = new ArrayList<Gorilla>();
    try (
      var in = new ObjectInputStream(new BufferedInputStream(
        new FileInputStream(dataFile)
      ));
    ) {
      while (true) {
        var object = in.readObject();
        if (object instanceof Gorilla g) {
          gorillas.add(g);
        }
      }
    } catch (EOFException e) {
      System.out.println("Load completed.");
    }
    return gorillas;
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    var gorillas = new ArrayList<Gorilla>();
    gorillas.add(new Gorilla("Grodd", 5, false));
    gorillas.add(new Gorilla("Ishmael", 8, true));
    
    File dataFile = new File("gorillas.data");
    saveToFile(gorillas, dataFile);
    
    var gorillasFromDisk = readFromFile(dataFile);
    System.out.println("gorillas from file");
    System.out.print(gorillasFromDisk);

  } // end main
} // end class

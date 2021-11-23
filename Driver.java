import java.io.*;
import java.util.*;

public class Driver {
  
  public static void main(String[] args) throws FileNotFoundException {
    String[] inputFiles = {"data-Trivial.txt", "data-Simple.txt", "data-Complex1.txt"};//, "data-Complex2.txt", "data-NearGridlock.txt", "data-Gridlock.txt"};

    //Call student solution for each test file:
    //for( int i=0; i<inputFiles.length; i++){
    for (String inputFile : inputFiles) {
      System.out.println("\n--------------- START OF OUTPUT FOR " + inputFile + " ---------------\n");
      TrafficSimulator.simulate(inputFile);
      System.out.println("\n--------------- END OF OUTPUT FOR " + inputFile + " ---------------\n\n");
    }
    
  }
}

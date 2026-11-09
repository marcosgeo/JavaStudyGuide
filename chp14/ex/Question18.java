package chp14.ex;

import java.io.*;

public class Question18 {

  public static void main(String[] args) throws IOException {
    var reader = new BufferedReader(new FileReader("./ex/peacocks.txt"));  // PEACOCKS
    var sb = new StringBuilder();
    sb.append((char)reader.read());  // P added
    reader.mark(10);
    for (int i = 0; i < 2; i++) {
      sb.append((char)reader.read());  // E added at 0; O added at 1;
      reader.skip(2);  // AC skipped at 0; CK skipped at 1;
    }
    reader.reset();    // position returned before to E
    reader.skip(0);    // no effect, position remains before E
    sb.append((char)reader.read());    // E added again
    System.out.println(sb.toString());    // PEOE
    reader.close();
  }

}

package chp05;

public class ClassRoom {
  public int roomNumber;
  protected static String teacherName;
  static int globalKey = 54321;
  public static int floor = 3;

  ClassRoom(int r, String t) {     // package access constructor
    roomNumber = r;
    teacherName = t;
  }
}


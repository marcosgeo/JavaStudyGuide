package my.city;

import my.school.*;

public class School {
  public static void main (String[] args) {
    System.out.println(ClassRoom.globalKey);   // does not compile
    ClassRoom room = new ClassRoom(101, "Mrs. Anderson");    // does not compile
    System.out.println(room.roomNumber);    // does not compile
    System.out.println(ClassRoom.floor);
    System.out.println(ClassRoom.teacherName);    // does not compile
  }
}

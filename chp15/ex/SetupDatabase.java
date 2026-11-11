package ex;

import java.sql.*;

/**
 * java -cp ".:ex/hsqldb-2.7.4.jar" ex.SetupDatabase
 */

public class SetupDatabase {
  public static void main(String[] args) throws Exception {
    String url = "jdbc:hsqldb:file:zoo";
    try (
      Connection conn = DriverManager.getConnection(url);
      Statement stmt = conn.createStatement()
    ) {
      dropExisting(conn);
      createTables(conn);
      createStoredProcedures(conn);
      printCount(conn, "SELECT count(*) FROM names");
    }
  }

  private static void run(Connection conn, String sql) throws SQLException {
    try(
      PreparedStatement ps = conn.prepareStatement(sql)
    ) {
      ps.executeUpdate();
    }
  }

  private static void printCount(Connection conn, String sql) throws SQLException {
    try (
      PreparedStatement ps = conn.prepareStatement(sql);
      ResultSet rs = ps.executeQuery()
    ) {
      rs.next();
      System.out.println(rs.getInt(1));
    }
  }

  private static void dropExisting(Connection conn) throws SQLException {
    run(conn, "DROP PROCEDURE read_e_names IF EXISTS");
    run(conn, "DROP PROCEDURE read_names_by_letter IF EXISTS");
    run(conn, "DROP PROCEDURE magic_number IF EXISTS");    
    run(conn, "DROP PROCEDURE double_number IF EXISTS");
    run(conn, "DROP TABLE names IF EXISTS");
    run(conn, "DROP TABLE exhibits IF EXISTS");
  }

  private static void createTables(Connection conn) throws SQLException {
    run(conn, """
        create table exhibits (
          id integer primary key,
          name varchar(255),
          num_acres decimal(4, 1)
        )
      """
    );

    run(conn, """
        create table names (
          id integer primary key,
          species_id integer references exhibits (id),
          name varchar(255)
        )
      """
    );

    run(conn, "insert into exhibits values (1, 'African Elephant', 7.5)");
    run(conn, "insert into exhibits values (2, 'Zebra', 1.2)");

    run(conn, "insert into names values (1, 1, 'Elsa')");
    run(conn, "insert into names values (2, 2, 'Zelda')");
    run(conn, "insert into names values (3, 1, 'Ester')");
    run(conn, "insert into names values (4, 1, 'Eddie')");
    run(conn, "insert into names values (5, 2, 'Zoe')");
  }

  private static void createStoredProcedures(Connection conn) throws SQLException {
    String noParams = """
      create procedure read_e_names()
      reads sql data dynamic result sets 1
      begin atomic
      declare result cursor with return for 
        select * from names where lower(name) like 'e%';
      open result;
      end
    """;

    String inParam = """
      create procedure read_names_by_letter(in prefix varchar(10))
      reads sql data dynamic result sets 1
      begin atomic
      declare result cursor with return for
        select * from names where lower(name) like concat(lower(prefix), '%');
      open result;
      end 
    """;

    String inOutParam = """
      create procedure double_number(inout num int) 
      reads sql data
      dynamic result sets 1
      begin atomic
      set num = num * 2;
      end
    """;

    String outParam = """
      create procedure magic_number(out num int)
      reads sql data
      begin atomic
      set num = 42;
      end
    """;

    run(conn, noParams);
    run(conn, inParam);
    run(conn, inOutParam);
    run(conn, outParam);
  }
}
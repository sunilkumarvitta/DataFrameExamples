package com.ex

object Ex9_ReadFromOracleDb {
  def main(args: Array[String]): Unit = {
    import java.sql.DriverManager
    // Loading the database driver// Loading the database driver

    Class.forName("oracle.jdbc.driver.OracleDriver")
    val conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "Temp1234")
    val stmt = conn.createStatement
    val resultSet = stmt.executeQuery("select * from employees")
    while ( {
      resultSet.next
    }) println(resultSet.getInt(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3))
    stmt.close
  }
}

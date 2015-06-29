package tools

import slick.codegen.SourceCodeGenerator

object Generator {

  def main(args: Array[String]): Unit = {

    println("start")

    val slickDriver = "slick.driver.MySQLDriver"
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val url = "jdbc:mysql://localhost:3306/playdemo"
    val outputFolder = "app/"
    val pkg = "models"
    val user = "root"
    val password = ""

    SourceCodeGenerator.main(
      Array(
        slickDriver,
        jdbcDriver,
        url,
        outputFolder,
        pkg,
        user,
        password
      ))

    println("end")
        
  }
}

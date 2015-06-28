import slick.codegen.SourceCodeGenerator

object Generator {

  def main(args: Array[String]): Unit = {

    println("start")

    val slickDriver = "slick.driver.H2Driver"
    val jdbcDriver = "org.h2.Driver"
    val url = "jdbc:h2:mem:demo"
    val outputFolder = "app/"
    val pkg = "models"

    SourceCodeGenerator.main(
      Array(
        slickDriver,
        jdbcDriver,
        url,
        outputFolder,
        pkg))

    println("end")
        
  }
}

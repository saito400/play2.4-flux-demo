name := """play2.4demo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.slick" %% "slick" % "3.0.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.0.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.flywaydb" %% "flyway-play" % "2.0.1",
  "mysql" % "mysql-connector-java" % "5.1.35",
  "com.typesafe.play" %% "play-slick" % "1.0.0"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

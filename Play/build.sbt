name := """Play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
//play.modules.enabled += "modules.GuiceModule"

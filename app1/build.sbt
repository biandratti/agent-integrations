name := """app1"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val app1 = (project in file("."))
  .enablePlugins(PlayScala)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

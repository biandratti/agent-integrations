name := """play-monitoring"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = project
  .in(file("."))
  .dependsOn(app1)
  .aggregate(app1)

scalaVersion := "2.13.10"

lazy val app1 = project

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

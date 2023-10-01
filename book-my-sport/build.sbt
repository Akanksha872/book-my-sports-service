name := """book-my-sport"""
organization := "com.sports"

version := "1.0-SNAPSHOT"

lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.10"

libraryDependencies += guice
libraryDependencies += ws

EclipseKeys.preTasks := Seq(compile in Compile, compile in Test)

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "8.0.32",
  "io.ebean" % "ebean" % "11.45.1",
  "org.webjars" % "swagger-ui" % "3.51.2",
  "commons-codec" % "commons-codec" % "1.15",
  "org.apache.commons" % "commons-lang3" % "3.12.0",
  "org.mockito" % "mockito-core" % "4.3.1" % Test
)

// Maually upgrading libraries because of BDBA scanner vulnerability detection
libraryDependencies += "org.springframework" % "spring-context" % "5.3.20"
libraryDependencies += "org.springframework" % "spring-core" % "5.3.20"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.4.2"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.2"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.11"
libraryDependencies += "ch.qos.logback" % "logback-core" % "1.2.11"

libraryDependencies += "org.yaml" % "snakeyaml" % "1.33"
libraryDependencies += "com.google.guava" % "guava" % "30.1.1-jre"

jacocoReportSettings := JacocoReportSettings()
  .withTitle("Jacoco Coverage Report")
  .withThresholds(JacocoThresholds(
      instruction = 70,
      method = 85,
      branch = 62,
      complexity = 59,
      line = 74,
      clazz = 89))
  .withFormats(JacocoReportFormats.XML)
  .withFileEncoding("utf-8")

jacocoExcludes := Seq(
  "Module",
  "router.*",
  "models.*",
  "models.rest.*",
  "enums.*",
  "enums.rest.*",
  "controllers.javascript.*",
  "controllers.Reverse*",
  "controllers.Routes*",
  "*.routes*"
)

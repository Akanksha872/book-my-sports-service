// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.15")

// Defines scaffolding (found under .g8 folder)
// http://www.foundweekends.org/giter8/scaffolding.html
// sbt "g8Scaffold form"
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.12.0")

// The Eclipse plugin
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")

// The Ebean plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "6.0.0")

// The Jacoco plugin
addSbtPlugin("com.github.sbt" % "sbt-jacoco" % "3.1.0")